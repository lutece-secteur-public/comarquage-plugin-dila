package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalFolderDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalFolderService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Implementation of {@link IDilaLocalFolderService}
 */
public class DilaLocalFolderService implements IDilaLocalFolderService, Serializable
{
    private static final String SEQ_ATTRIBUTE = "seq";

    private static final String TITLE_TAG = "Titre";

    private static final String ID_ATTRIBUTE = "ID";

    private static final String FOLDER_TAG = "Dossier";

    /** The serial ID */
    private static final long serialVersionUID = -8207832026211506607L;

    @Inject
    @Named( "dilaLocalFolderDAO" )
    private ILocalFolderDAO _dilaLocalFolderDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create( LocalFolderDTO dossier )
    {
        return _dilaLocalFolderDAO.create( dossier );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findFolderIdByLocalId( String idLocal )
    {
        return _dilaLocalFolderDAO.findFolderIdByLocalId( idLocal );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( Long localFolderId )
    {
        _dilaLocalFolderDAO.delete( localFolderId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalFolderDTO findFolderByLocalId( Long idLocal )
    {
        return _dilaLocalFolderDAO.findFolderByLocalId( idLocal );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( LocalFolderDTO folder )
    {
        _dilaLocalFolderDAO.store( folder );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document insertFolderLinks( String parentId, DocumentBuilder builder, Document document )
    {
        List<LocalFolderDTO> folderList = this.findLocalFoldersByParentTheme( parentId );
        for ( LocalFolderDTO currentFolder : folderList )
        {
            NodeList folders = document.getElementsByTagName( FOLDER_TAG );
            Element newFolder = document.createElement( FOLDER_TAG );
            newFolder.setAttribute( ID_ATTRIBUTE, currentFolder.getLocalDTO( ).getId( ).toString( ) );
            Element newTitle = document.createElement( TITLE_TAG );
            newTitle.setTextContent( currentFolder.getLocalDTO( ).getTitle( ) );
            newFolder.appendChild( newTitle );
            boolean hasToShift = false;
            for ( int i = 0; i < folders.getLength( ); i++ )
            {
                Element folderElement = (Element) folders.item( i );
                String seq = folderElement.getAttribute( SEQ_ATTRIBUTE );

                if ( folderElement.getAttribute( ID_ATTRIBUTE ).equals( currentFolder.getSiblingFolderId( ) ) )
                {
                    if ( currentFolder.getPosition( ) == 1 )
                    {
                        if ( seq != null )
                        {
                            newFolder.setAttribute( SEQ_ATTRIBUTE, "" + Integer.parseInt( seq ) );
                            folderElement.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                        }
                        folderElement.getParentNode( ).insertBefore( newFolder, folderElement );
                    }
                    else
                    {
                        if ( seq != null )
                        {
                            newFolder.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                        }
                        folderElement.getParentNode( ).insertBefore( newFolder, folderElement.getNextSibling( ) );
                    }
                    i++;
                    hasToShift = true;
                }
                else if ( hasToShift && seq != null )
                {
                    folderElement.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                }
            }
            document.getDocumentElement( ).normalize( );
        }

        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalFolderDTO> findLocalFoldersByParentTheme( String idParent )
    {
        return _dilaLocalFolderDAO.findLocalFoldersByParentTheme( idParent );
    }

}
