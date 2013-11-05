package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;

import java.util.List;


/**
 * Service for {@link LocalFolderLinkDTO}
 * @author rputegnat
 */
public interface IDilaLocalFolderLinkService
{
    /**
     * Create a {@link LocalFolderLinkDTO}
     * @param link the {@link LocalFolderLinkDTO} to create
     */
    void create( LocalFolderLinkDTO link );

    /**
     * Delete links by their folder id
     * @param localFolderId the folder id to delete
     */
    void deleteByFolderId( Long localFolderId );

    /**
     * Find {@link List} of {@link LocalFolderLinkDTO} by folder Id
     * @param id the folder id
     * @return list of corresponding {@link LocalFolderLinkDTO}
     */
    List<LocalFolderLinkDTO> findByFolderId( Long id );
}
