/**
 * 
 */
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.service.IDilaPivotLocalService;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This class provides a method to find XML filenames according to code pivot
 * 
 */
public class DilaPivotLocalService implements IDilaPivotLocalService, Serializable
{
    private static final String TAG_PIVOT_LOCAL = "PivotLocal";
    private static final String ATTR_ID = "id";
    private static final String TAG_ORGANISME = "Organisme";
    private static final String ATTR_PIVOT_LOCAL = "pivotLocal";
    private static final String TAG_TYPE_ORGANISME = "TypeOrganisme";
    /** Serial ID */
    private static final long serialVersionUID = -2515296117212667308L;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findByCodePivot( String strCodePivot )
    {
        List<String> fileNames = new ArrayList<String>( );
        String insee = AppPropertiesService.getProperty( DilaConstants.PROPERTY_INSEE );
        String townDirectory = AppPropertiesService
                .getProperty( DilaConstants.PROPERTY_XML_DIRECTORY_LOCALES_TOWN );
        File town = new File( townDirectory + insee.substring( 0, 2 ) + File.separator + insee
                + DilaConstants.XML_EXTENSION );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder( );
            Document document = builder.parse( town );
            document.getDocumentElement( ).normalize( );
            NodeList pivots = document.getElementsByTagName( TAG_TYPE_ORGANISME );
            for ( int i = 0; i < pivots.getLength( ); i++ )
            {
                Element pivotNode = (Element) pivots.item( i );
                if ( pivotNode != null && strCodePivot.equals( pivotNode.getAttribute( ATTR_PIVOT_LOCAL ) ) )
                {
                    NodeList listOrganisms = pivotNode.getElementsByTagName( TAG_ORGANISME );
                    for ( int j = 0; j < listOrganisms.getLength( ); j++ )
                    {
                        Element organism = (Element) listOrganisms.item( j );
                        if ( organism != null )
                        {
                            fileNames.add( organism.getAttribute( ATTR_ID ) );
                        }
                    }
                    break;
                }
            }
        }
        catch ( ParserConfigurationException e )
        {
            AppLogService.error( e );
        }
        catch ( SAXException e )
        {
            AppLogService.error( e );
        }
        catch ( IOException e )
        {
            AppLogService.error( e );
        }

        return fileNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document insertPivots( DocumentBuilder builder, Document document ) throws SAXException, IOException
    {
        NodeList pivots = document.getElementsByTagName( TAG_PIVOT_LOCAL );
        for ( int i = 0; i < pivots.getLength( ); i++ )
        {
            Node pivotNode = pivots.item( i );
            if ( pivotNode != null )
            {
                String codePivot = pivotNode.getTextContent( );
                pivotNode.setTextContent( StringUtils.EMPTY );
                List<String> localOrganisms = this.findByCodePivot( codePivot );
                if ( CollectionUtils.isEmpty( localOrganisms ) )
                {
                    pivotNode.getParentNode( ).removeChild( pivotNode );
                    i--;
                }
                else
                {
                    for ( String organism : localOrganisms )
                    {
                        String[] splitOrganisme = organism.split( "-" );
                        String directoryOrganisme = AppPropertiesService
                                .getProperty( DilaConstants.PROPERTY_XML_DIRECTORY_LOCALES_ORGANISMS );
                        File pivotFile = new File( directoryOrganisme + splitOrganisme[1].substring( 0, 2 )
                                + File.separator + organism + DilaConstants.XML_EXTENSION );
                        Document documentPivot = builder.parse( pivotFile );

                        Node pivot = documentPivot.getFirstChild( );
                        Node importedPivot = document.importNode( pivot, true );
                        pivotNode.appendChild( importedPivot );
                    }
                }
                document.getDocumentElement( ).normalize( );
            }

        }
        return document;
    }
}
