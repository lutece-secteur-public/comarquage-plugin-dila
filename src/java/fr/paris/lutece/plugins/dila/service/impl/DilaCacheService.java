/**
 * 
 */
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.enums.ContentTypeEnum;
import fr.paris.lutece.plugins.dila.business.enums.ResourceTypeEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.business.stylesheet.dto.DilaStyleSheet;
import fr.paris.lutece.plugins.dila.service.IDilaCacheService;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalCardService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalFolderService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaPivotLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaStyleSheetService;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.plugins.dila.utils.CacheKeyUtils;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import fr.paris.lutece.portal.service.html.XmlTransformerService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * @author seblopez
 */
public class DilaCacheService extends AbstractCacheableService implements IDilaCacheService, Serializable
{
    private static final String ERROR = "Error";

    private static final String ASSOCIATIONS_THEMES_ID = "N20";

    /** Serial ID */
    private static final long serialVersionUID = -8869528327826773129L;

    private static final String SERVICE_NAME = "Dila Cache Service";

    @Inject
    @Named( "dilaPivotLocalService" )
    private IDilaPivotLocalService _dilaPivotLocalService;

    @Inject
    @Named( "dilaXmlService" )
    private IDilaXmlService _dilaXmlService;

    @Inject
    @Named( "dilaStyleSheetService" )
    private IDilaStyleSheetService _dilaStyleSheetService;

    @Inject
    @Named( "dilaLocalCardService" )
    private IDilaLocalCardService _dilaLocalCardService;

    @Inject
    @Named( "dilaLocalFolderService" )
    private IDilaLocalFolderService _dilaLocalFolderService;

    @Inject
    @Named( "dilaLocalService" )
    private IDilaLocalService _dilaLocalService;

    @Inject
    @Named( "dilaComplementaryDataService" )
    private IDilaComplementaryDataService _dilaComplementaryDataService;

    /**
     * Default constructor
     */
    public DilaCacheService( )
    {
        initCache( );
    }

    @Override
    public String getName( )
    {
        return SERVICE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public String getRessource( String strCacheKey, Locale locale )
    {
        String dilaPage = (String) getFromCache( strCacheKey );
        if ( dilaPage == null )
        {
            dilaPage = buildDilaPage( strCacheKey, locale );
            putInCache( strCacheKey, dilaPage );
        }
        return dilaPage;
    }

    /**
     * Build HTML code for given key
     * @param strCacheKey the dila cache key
     * @param locale the locale
     * @return the HTML code to display
     */
    private String buildDilaPage( String strCacheKey, Locale locale )
    {
        Long categoryId = CacheKeyUtils.getCategoryFromCacheKey( strCacheKey );
        AudienceCategoryEnum audienceEnum = AudienceCategoryEnum.fromId( categoryId );
        String cardId = CacheKeyUtils.getCardIdFromCacheKey( strCacheKey );
        String xmlDirectory = null;
        switch ( audienceEnum )
        {
        case ASSOCIATIONS:
            xmlDirectory = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_DIRECTORY_ASSO );
            break;
        case INDIVIDUALS:
            xmlDirectory = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_DIRECTORY_INDIVIDUALS );
            break;
        case PROFESSIONNALS:
            xmlDirectory = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_DIRECTORY_PROFESSIONALS );
            break;
        default:
            xmlDirectory = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_DIRECTORY );
            break;
        }

        String resourceType = null;
        String xslName = null;
        File xmlFile = null;
        InputStream xmlStream = null;
        String htmlCode = null;
        Long contentType = null;
        // if fiche id is numeric, it is a local card
        if ( StringUtils.isNumeric( cardId ) )
        {
            String xml = _dilaLocalService.findXmlById( Long.valueOf( cardId ) );
            xmlStream = new ByteArrayInputStream( xml.getBytes( ) );
            contentType = ContentTypeEnum.LOCAL.getId( );
        }
        else
        {
            if ( DilaConstants.DEFAULT_XML.equals( cardId ) )
            {
                if ( audienceEnum.equals( AudienceCategoryEnum.PROFESSIONNALS ) )
                {
                    contentType = ContentTypeEnum.PROFESSIONAL_THEMES.getId( );
                }
                else
                {
                    contentType = ContentTypeEnum.THEMES.getId( );
                }
            }
            else
            {
                resourceType = _dilaXmlService.findResourceTypeByIdXMLAndAudience( cardId, categoryId );
                if ( resourceType == null )
                {
                    return htmlCode;
                }
                ResourceTypeEnum resourceTypeEnum = ResourceTypeEnum.fromLabel( resourceType );
                contentType = resourceTypeEnum.getContentType( );
            }
            xmlFile = new File( xmlDirectory + cardId + DilaConstants.XML_EXTENSION );
        }
        List<DilaStyleSheet> dilaStyleSheets = _dilaStyleSheetService.getDilaStyleSheetList( contentType.intValue( ),
                null );
        if ( CollectionUtils.isNotEmpty( dilaStyleSheets ) )
        {
            xslName = dilaStyleSheets.get( 0 ).getFile( );
        }
        File styleSheet = new File( AppPathService.getPath( DilaConstants.PROPERTY_PATH_XSL ) + xslName );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder( );
            Document document = null;
            if ( xmlFile != null )
            {
                document = builder.parse( xmlFile );
            }
            else
            {
                document = builder.parse( xmlStream );
            }
            document.getDocumentElement( ).normalize( );
            document = _dilaPivotLocalService.insertPivots( builder, document );
            switch ( ContentTypeEnum.fromId( contentType ) )
            {
            case NODE:
                document = _dilaLocalCardService.insertCardLinks( cardId, builder, document );
                document = _dilaLocalFolderService.insertFolderLinks( cardId, builder, document );
                if ( AudienceCategoryEnum.ASSOCIATIONS.equals( audienceEnum ) && ASSOCIATIONS_THEMES_ID.equals( cardId ) )
                {
                    document = _dilaLocalService.insertLastCardsLinks( categoryId, builder, document );
                }
                break;
            case THEMES:
            case PROFESSIONAL_THEMES:
                document = _dilaXmlService.insertHowToLinks( categoryId, builder, document );
                document = _dilaLocalService.insertLastCardsLinks( categoryId, builder, document );
                break;
            case CARD:
                if ( !StringUtils.isNumeric( cardId ) )
                {
                    Long cardTechnicalId = _dilaXmlService.findIdByXmlAndAudience( cardId, categoryId );
                    document = _dilaComplementaryDataService.insertComplementaryData( cardTechnicalId, categoryId,
                            builder, document );
                }
                break;
            default:
                break;
            }

            // Get RSS data for national cards
            if ( !StringUtils.isNumeric( cardId ) )
            {
                document = _dilaXmlService.insertRssLinks( builder, document, locale );
            }

            // Use a Transformer for output
            TransformerFactory tFactory = TransformerFactory.newInstance( );
            Transformer transformer = tFactory.newTransformer( );
            DOMSource source = new DOMSource( document );
            StringWriter writer = new StringWriter( );
            StreamResult result = new StreamResult( writer );
            transformer.transform( source, result );
            htmlCode = writer.toString( );

            Map<String, String> params = new HashMap<String, String>( );
            params.put( DilaConstants.MARK_XMLURL_PARAM, xmlDirectory );
            params.put( DilaConstants.MARK_CATEGORY_PARAM, audienceEnum.getLabel( ).toLowerCase( ) );
            StringBuilder referer = new StringBuilder( );
            referer.append( DilaConstants.XPAGE_REFERER );
            referer.append( audienceEnum.getLabel( ).toLowerCase( ) );
            referer.append( DilaConstants.XPAGE_REFERER_XMLFILE );
            params.put( DilaConstants.MARK_REFERER_PARAM, referer.toString( ) );
            params.put( DilaConstants.MARK_CARDID_PARAM, cardId );
            // Set parameters for "how to ..." section link
            XmlDTO homeHowTo = _dilaXmlService.findHomeHowTo( categoryId );
            if ( homeHowTo != null )
            {
                params.put( DilaConstants.MARK_HOW_TO_ID_PARAM, homeHowTo.getIdXml( ) );
                params.put( DilaConstants.MARK_HOW_TO_TITLE_PARAM, homeHowTo.getTitle( ) );
            }
            StreamSource xslSource = new StreamSource( styleSheet );
            XmlTransformerService trans = new XmlTransformerService( );
            htmlCode = trans.transformBySourceWithXslCache( htmlCode, xslSource, xslName, params, null );
            if ( htmlCode.startsWith( ERROR ) )
            {
                return null;
            }
        }
        catch ( ParserConfigurationException ex )
        {
            AppLogService.error( ex );
        }
        catch ( SAXException e )
        {
            AppLogService.error( e );
        }
        catch ( IOException e )
        {
            AppLogService.error( e );
        }
        catch ( TransformerConfigurationException e )
        {
            AppLogService.error( e );
        }
        catch ( TransformerException e )
        {
            AppLogService.error( e );
        }

        return htmlCode;
    }
}
