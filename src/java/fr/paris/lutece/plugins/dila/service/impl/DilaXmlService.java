package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IXmlDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Implementation of {@link IDilaXmlService}
 */
public class DilaXmlService implements IDilaXmlService, Serializable
{
    private static final String ID_ATTRIBUTE = "ID";

    private static final String HOW_TO_TAG = "CommentFaire";

    private static final String ERROR_RSS_FEED = "dila.error.rssFeed";

    private static final String ITEM_TAG = "item";

    private static final String HTTP_ACCESS_PROXY_PASSWORD = "httpAccess.proxyPassword";

    private static final String HTTP_ACCESS_PROXY_USERNAME = "httpAccess.proxyUserName";

    private static final String HTTP_ACCESS_PROXY_PORT = "httpAccess.proxyPort";

    private static final String URL_ATTR = "URL";

    private static final String TYPE_ATTR_FIL = "Fil";

    private static final String TYPE_ATTR = "type";

    private static final String ACTUALITE_TAG = "Actualite";

    private static final String ERROR_TAG = "Error";

    private static final String HTTP_ACCESS_PROXY_HOST = "httpAccess.proxyHost";

    /** Serial ID */
    private static final long serialVersionUID = 3901887734209752792L;

    @Inject
    @Named( "dilaXmlDAO" )
    private IXmlDAO _dilaXmlDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public String findTitleById( String strId )
    {
        return _dilaXmlDAO.findTitleById( strId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XmlDTO findFolderById( String strId )
    {
        return _dilaXmlDAO.findFolderById( strId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findTitleByIdAndTypesAndAudience( String strIdDossierFrere, List<String> listAvailableTypes,
            Long lIdAudience )
    {
        return _dilaXmlDAO.findTitleByIdAndTypesAndAudience( strIdDossierFrere, listAvailableTypes, lIdAudience );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XmlDTO findByIdAndTypesAndAudience( String strId, List<String> listAvailableTypes, Long lIdAudience )
    {
        return _dilaXmlDAO.findByIdAndTypesAndAudience( strId, listAvailableTypes, lIdAudience );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String findResourceTypeByIdXMLAndAudience( String strIdXml, Long lIdAudience )
    {
        return _dilaXmlDAO.findResourceTypeByIdXMLAndAudience( strIdXml, lIdAudience );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<XmlDTO> findAll( )
    {
        return _dilaXmlDAO.findAll( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document insertHowToLinks( Long lAudienceId, DocumentBuilder builder, Document document )
    {
        List<XmlDTO> xmlList = this.findHowToByAudience( lAudienceId );
        for ( XmlDTO currentXML : xmlList )
        {
            Element newCard = document.createElement( HOW_TO_TAG );
            newCard.setAttribute( ID_ATTRIBUTE, currentXML.getIdXml( ) );
            newCard.setTextContent( currentXML.getTitle( ) );
            document.getDocumentElement( ).appendChild( newCard );
            document.getDocumentElement( ).normalize( );
        }

        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findIdByXmlAndAudience( String strXmlName, Long lAudienceId )
    {
        return _dilaXmlDAO.findIdByXmlAndAudience( strXmlName, lAudienceId );
    }

    /**
     * Find all "Comment faire si" resources for audience
     * @param audienceId the audience to check
     * @return the list of corresponding {@link XmlDTO}
     */
    private List<XmlDTO> findHowToByAudience( Long audienceId )
    {
        return _dilaXmlDAO.findHowToByAudience( audienceId );
    }

    /**
     * {@inheritDoc}
     */
    public Document insertRssLinks( DocumentBuilder builder, Document document, Locale locale )
    {
        CloseableHttpClient client = null;
        HttpClientBuilder clientBuilder = HttpClients.custom( );
        String proxyHost = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_HOST );
        Node errorNode = document.createElement( ERROR_TAG );
        errorNode.setTextContent( I18nService.getLocalizedString( ERROR_RSS_FEED, locale ) );
        NodeList news = document.getElementsByTagName( ACTUALITE_TAG );
        for ( int i = 0; i < news.getLength( ); i++ )
        {
            Element newsElement = (Element) news.item( i );
            if ( newsElement != null && newsElement.getAttribute( TYPE_ATTR ).contains( TYPE_ATTR_FIL ) )
            {
                String rssUrl = newsElement.getAttribute( URL_ATTR );
                HttpGet request = new HttpGet( rssUrl );
                // Connection through proxy
                if ( StringUtils.isNotBlank( proxyHost ) )
                {
                    String proxyPort = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_PORT );
                    String proxyUser = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_USERNAME );
                    String proxyPassword = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_PASSWORD );

                    //Proxy authentication
                    if ( StringUtils.isNotBlank( proxyUser ) )
                    {
                        CredentialsProvider credsProvider = new BasicCredentialsProvider( );
                        credsProvider.setCredentials( new AuthScope( proxyHost, Integer.parseInt( proxyPort ) ),
                                new UsernamePasswordCredentials( proxyUser, proxyPassword ) );
                        clientBuilder.setDefaultCredentialsProvider( credsProvider );
                    }
                    String strSchema = request.getURI( ).getScheme( );
                    HttpHost proxy = new HttpHost( proxyHost, Integer.parseInt( proxyPort ), strSchema );
                    RequestConfig config = RequestConfig.custom( ).setProxy( proxy ).build( );
                    request.setConfig( config );
                }
                client = clientBuilder.build( );
                CloseableHttpResponse response = null;
                try
                {
                    response = client.execute( request );
                    int nResponseStatusCode = response.getStatusLine( ).getStatusCode( );
                    if ( nResponseStatusCode != HttpURLConnection.HTTP_OK )
                    {
                        newsElement.appendChild( errorNode );
                        continue;
                    }
                    InputStream rssFeed = response.getEntity( ).getContent( );
                    StringWriter writer = new StringWriter( );
                    IOUtils.copy( rssFeed, writer );
                    rssFeed = new ByteArrayInputStream( writer.toString( ).getBytes( ) );
                    Document rssDoc = builder.parse( rssFeed );
                    NodeList items = rssDoc.getElementsByTagName( ITEM_TAG );
                    for ( int j = 0; j < items.getLength( ) && j < 4; j++ )
                    {
                        Node currentItem = items.item( j );
                        Node importedNode = document.importNode( currentItem, true );
                        newsElement.appendChild( importedNode );
                        document.getDocumentElement( ).normalize( );
                    }
                }
                catch ( ClientProtocolException e )
                {
                    AppLogService.error( "Error on HTTP protocol", e );
                    newsElement.appendChild( errorNode );
                    continue;
                }
                catch ( IOException e )
                {
                    AppLogService.error( "Error on execute request", e );
                    newsElement.appendChild( errorNode );
                    continue;
                }
                catch ( SAXException e )
                {
                    AppLogService.error( "Error parsing feed", e );
                    newsElement.appendChild( errorNode );
                    continue;
                }
                try
                {
                    response.close( );
                }
                catch ( IOException e )
                {
                    AppLogService.error( "Error closing response", e );
                    continue;
                }
            }
        }
        return document;
    }

    /**
     * {@inheritDoc}
     */
    public XmlDTO findHomeHowTo( Long audienceId )
    {
        return _dilaXmlDAO.findHomeHowTo( audienceId );
    }
}
