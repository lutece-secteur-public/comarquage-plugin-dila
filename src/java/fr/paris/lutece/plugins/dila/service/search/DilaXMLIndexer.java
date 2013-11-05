package fr.paris.lutece.plugins.dila.service.search;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.search.PageIndexer;
import fr.paris.lutece.portal.service.search.SearchIndexer;
import fr.paris.lutece.portal.service.search.SearchItem;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;


/**
 * DILA Xml indexer
 * @author rputegnat
 */
public class DilaXMLIndexer implements SearchIndexer
{
    public static final String INDEX_TYPE_XML = "DILA XML";
    public static final String INDEXER_NAME = "DilaXMLIndexer";
    protected static final String PARAMETER_PAGE_ID = "xmlFile";
    protected static final String PARAMETER_PAGE_CATEGORIE = "categorie";
    protected static final String PROPERTY_PAGE_BASE_URL = "dila.pageIndexer.baseUrl";
    protected static final String PROPERTY_SEARCH_PAGE_URL = "search.pageSearch.baseUrl";
    protected static final String PROPERTY_INDEXER_ENABLE = "dila.pageIndexer.enable";
    private static final String INDEXER_DESCRIPTION = "DILA service for XML";
    private static final String INDEXER_VERSION = "1.0.0";

    private IDilaXmlService _dilaXmlService = SpringContextService.getBean( "dilaXmlService" );

    @Override
    public void indexDocuments( ) throws IOException, InterruptedException, SiteMessageException
    {
        List<XmlDTO> listXml = _dilaXmlService.findAll( );

        for ( XmlDTO xml : listXml )
        {
            Document doc = null;

            try
            {
                doc = getDocument( xml );
            }
            catch ( Exception e )
            {
                String strMessage = "Page ID : " + xml.getId( );
                IndexationService.error( this, e, strMessage );
            }

            if ( doc != null )
            {
                IndexationService.write( doc );
            }
        }
    }

    @Override
    public List<Document> getDocuments( String strIdDocument ) throws IOException, InterruptedException,
            SiteMessageException
    {
        return null;
    }

    @Override
    public String getName( )
    {
        return INDEXER_NAME;
    }

    @Override
    public String getVersion( )
    {
        return INDEXER_VERSION;
    }

    @Override
    public String getDescription( )
    {
        return INDEXER_DESCRIPTION;
    }

    @Override
    public boolean isEnable( )
    {
        String strEnable = AppPropertiesService.getProperty( PROPERTY_INDEXER_ENABLE, "true" );

        return ( strEnable.equalsIgnoreCase( "true" ) );
    }

    @Override
    public List<String> getListType( )
    {
        List<String> listType = new ArrayList<String>( );
        listType.add( PageIndexer.INDEX_TYPE_PAGE );

        return listType;
    }

    @Override
    public String getSpecificSearchAppUrl( )
    {
        return AppPropertiesService.getProperty( PROPERTY_SEARCH_PAGE_URL );
    }

    /**
     * Builds a document which will be used by Lucene during the indexing of the
     * pages of the site with the following
     * fields : summary, uid, url, contents, title and description.
     * @return the built Document
     * @param xml the xml to index
     * @throws IOException The IO Exception
     * @throws InterruptedException The InterruptedException
     * @throws SiteMessageException occurs when a site message need to be
     *             displayed
     */
    protected Document getDocument( XmlDTO xml ) throws IOException, InterruptedException, SiteMessageException
    {
        String strPageBaseUrl = AppPropertiesService.getProperty( PROPERTY_PAGE_BASE_URL );
        // make a new, empty document
        Document doc = new Document( );

        // Add the url as a field named "url".  Use an UnIndexed field, so
        // that the url is just stored with the document, but is not searchable.
        doc.add( new Field( SearchItem.FIELD_TYPE, xml.getResourceType( ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        String strDate = null;

        if ( xml.getModificationDate( ) != null )
        {
            strDate = DateTools.dateToString( xml.getModificationDate( ), DateTools.Resolution.DAY );
        }
        else
        {
            strDate = DateTools.dateToString( xml.getCreationDate( ), DateTools.Resolution.DAY );
        }
        doc.add( new Field( SearchItem.FIELD_DATE, strDate, Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        // Add the url as a field named "url".  Use an UnIndexed field, so
        // that the url is just stored with the document, but is not searchable.
        UrlItem url = new UrlItem( strPageBaseUrl );
        url.addParameter( PARAMETER_PAGE_ID, xml.getIdXml( ) );
        url.addParameter( PARAMETER_PAGE_CATEGORIE, AudienceCategoryEnum.fromId( xml.getIdAudience( ) )
                .getLabel( ) );

        doc.add( new Field( SearchItem.FIELD_URL, url.getUrl( ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        StringBuilder content = new StringBuilder( );
        content.append( xml.getIdXml( ) );
        content.append( " " );
        content.append( xml.getTitle( ) );

        doc.add( new Field( SearchItem.FIELD_CONTENTS, content.toString( ), Field.Store.NO, Field.Index.ANALYZED ) );

        // Add the uid as a field, so that index can be incrementally maintained.
        // This field is not stored with document, it is indexed, but it is not
        // tokenized prior to indexing.
        String strIdPage = xml.getIdXml( );
        doc.add( new Field( SearchItem.FIELD_UID, strIdPage, Field.Store.NO, Field.Index.NOT_ANALYZED ) );

        // Add the tag-stripped contents as a Reader-valued Text field so it will
        // get tokenized and indexed.
        doc.add( new Field( SearchItem.FIELD_TITLE, xml.getTitle( ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        // return the document
        return doc;
    }
}
