package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IXmlDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.IDilaBatchXMLService;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;


/**
 * Implementation of {@link IDilaBatchXMLService}
 */
public class DilaBatchXMLService implements IDilaBatchXMLService
{
    private static final String RESSOURCE_TYPE = "Ressource";

    /**
     * List of XML file that will not be indexed
     */
    public static final List<String> listXMLNotSupported = Arrays.asList( "themes.xml", "redirections.xml",
            "journal.xml" );

    /**
     * SAX builder
     */
    private static final SAXBuilder _saxBuilder = new SAXBuilder( );

    /**
     * URI for namespace "dc"
     */
    private static final String NAMESPACE_URI_DC = "http://purl.org/dc/elements/1.1/";

    /**
     * Namespace "dc"
     */
    private static final Namespace NAMESPACE_DC = Namespace.getNamespace( "dc", NAMESPACE_URI_DC );

    /**
     * XML attribute : ID
     */
    private static final String ATTRIBUTE_ID = "ID";

    /**
     * XML child : title
     */
    private static final String CHILD_TITLE = "title";

    /**
     * XML child : type
     */
    private static final String CHILD_TYPE = "type";

    /**
     * XML child : FilDAriane
     */
    private static final String CHILD_BREADCRUMB = "FilDAriane";

    @Inject
    @Named( "dilaXmlDAO" )
    private IXmlDAO _dilaXmlDAO;

    @Override
    public void processXMLFile( File file, AudienceCategoryEnum typeXML ) throws JDOMException, IOException
    {
        AppLogService.debug( "Processing file : " + file.getName( ) );
        if ( DilaBatchXMLService.listXMLNotSupported.contains( file.getName( ).toLowerCase( ) ) )
        {
            AppLogService.debug( "File not indexed" );
        }
        else
        {
            // DILA XML
            XmlDTO dilaXml = buildDocument( file, typeXML );

            // Create or update XML
            List<XmlDTO> listDilaXmls = _dilaXmlDAO.findAll( );
            int nIndex = listDilaXmls.indexOf( dilaXml );
            if ( nIndex != -1 )
            {
                XmlDTO dilaXmlExist = listDilaXmls.get( nIndex );

                AppLogService.debug( "Update" );
                dilaXml.setId( dilaXmlExist.getId( ) );
                _dilaXmlDAO.store( dilaXml );
            }
            else
            {
                AppLogService.debug( "Create" );
                _dilaXmlDAO.create( dilaXml );
            }

        }

    }

    @Override
    public XmlDTO buildDocument( File file, AudienceCategoryEnum typeXML ) throws JDOMException, IOException
    {
        XmlDTO dilaXml = new XmlDTO( );
        dilaXml.setIdAudience( typeXML.getId( ) );

        // Convert file to document object
        Document document = _saxBuilder.build( file );

        // Get root
        Element root = document.getRootElement( );

        // Get attribute ID
        Attribute rootAttrId = root.getAttribute( ATTRIBUTE_ID );
        if ( rootAttrId != null )
        {
            String strId = rootAttrId.getValue( );
            dilaXml.setIdXml( strId );
        }

        // Get title
        Element title = root.getChild( CHILD_TITLE, NAMESPACE_DC );
        if ( title != null )
        {
            String strTitle = title.getTextTrim( );
            dilaXml.setTitle( strTitle );
        }

        // Get type
        Element type = root.getChild( CHILD_TYPE, NAMESPACE_DC );
        if ( type != null )
        {
            if ( file.getName( ).startsWith( "R" ) )
            {
                dilaXml.setResourceType( RESSOURCE_TYPE );
            }
            else
            {
                String strType = type.getTextTrim( );
                dilaXml.setResourceType( strType );
            }
        }

        // Get breadcrumb
        Element rootBreadcrumb = root.getChild( CHILD_BREADCRUMB );
        if ( rootBreadcrumb != null )
        {
            List<Element> listBreadcrumb = rootBreadcrumb.getChildren( );
            if ( CollectionUtils.isNotEmpty( listBreadcrumb ) )
            {
                List<String> listBreadcrumbId = new ArrayList<String>( listBreadcrumb.size( ) );
                for ( Element elementBreadcrumb : listBreadcrumb )
                {
                    listBreadcrumbId.add( elementBreadcrumb.getAttributeValue( ATTRIBUTE_ID ) );
                }
                String strBreadcrumb = StringUtils.join( listBreadcrumbId, ";" );
                dilaXml.setBreadcrumb( strBreadcrumb );
            }
        }
        return dilaXml;
    }

    @Override
    public void delete( )
    {
        _dilaXmlDAO.delete( );
    }
}
