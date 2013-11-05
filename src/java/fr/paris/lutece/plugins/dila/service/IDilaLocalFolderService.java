package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;


/**
 * Interface service for {@link LocalFolderDTO}
 */
public interface IDilaLocalFolderService
{
    /**
     * Create a {@link LocalFolderDTO}
     * @param folder the folder to create
     * @return the id of new {@link LocalFolderDTO}
     */
    Long create( LocalFolderDTO folder );

    /**
     * Find folde id by its local id
     * @param localId the local id to find
     * @return the id of corresponding {@link LocalFolderDTO}
     */
    Long findFolderIdByLocalId( String localId );

    /**
     * Delete a {@link LocalFolderDTO}
     * @param localFolderId the id to delete
     */
    void delete( Long localFolderId );

    /**
     * Find a {@link LocalFolderDTO} by its local id
     * @param localId the local id to find
     * @return the corresponding local folder
     */
    LocalFolderDTO findFolderByLocalId( Long localId );

    /**
     * Update a {@link LocalFolderDTO}
     * @param folder the {@link LocalFolderDTO} to update
     */
    void update( LocalFolderDTO folder );

    /**
     * Insert local folders links into document
     * @param parentId the parent id
     * @param builder the builder to use
     * @param document the document to complete
     * @return the document with links
     */
    Document insertFolderLinks( String parentId, DocumentBuilder builder, Document document );

    /**
     * Find all {@link LocalFolderDTO} attached to parent folder
     * @param idParent the parent folder id
     * @return the attached local cards
     */
    List<LocalFolderDTO> findLocalFoldersByParentTheme( String idParent );

}
