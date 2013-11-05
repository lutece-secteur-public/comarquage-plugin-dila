package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;

import java.util.List;


/**
 * DAO interface for {@link LocalFolderLinkDTO}
 */
public interface ILocalFolderLinkDAO
{
    /**
     * Create a {@link LocalFolderLinkDTO}
     * @param link the {@link LocalFolderLinkDTO} to create
     */
    void insert( LocalFolderLinkDTO link );

    /**
     * Delete links by their folder id
     * @param localFolderId the folder id to delete
     */
    void deleteByFolderId( Long localFolderId );

    /**
     * Find {@link List} of {@link LocalFolderLinkDTO} by folder Id
     * @param folderId the folder id
     * @return list of corresponding {@link LocalFolderLinkDTO}
     */
    List<LocalFolderLinkDTO> findByFolderId( Long folderId );
}
