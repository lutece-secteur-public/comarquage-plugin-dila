package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;

import java.util.List;


/**
 * Interface DAO for {@link LocalFolderDTO}
 */
public interface ILocalFolderDAO
{
    /**
     * Create a {@link LocalFolderDTO}
     * @param folder the fodler to create
     * @return the id of new {@link LocalFolderDTO}
     */
    Long create( LocalFolderDTO folder );

    /**
     * Find folder id by its local id
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
     * @return the corresponding local id
     */
    LocalFolderDTO findFolderByLocalId( Long localId );

    /**
     * Update a {@link LocalFolderDTO}
     * @param folder the {@link LocalFolderDTO} to update
     */
    void store( LocalFolderDTO folder );

    /**
     * Find all {@link LocalFolderDTO} attached to parent theme
     * @param parentId the parent theme id
     * @return the attached local folders
     */
    List<LocalFolderDTO> findLocalFoldersByParentTheme( String parentId );
}
