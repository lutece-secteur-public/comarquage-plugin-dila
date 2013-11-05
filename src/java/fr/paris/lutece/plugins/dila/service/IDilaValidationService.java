package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;

import java.util.List;


/**
 * Service for validation
 */
public interface IDilaValidationService
{
    /**
     * Validate a folder
     * @param folderBean the folder to validate
     * @param listLinks the links to validate
     * @return error key or null
     */
    String validateLocalFolder( LocalFolderDTO folderBean, List<LocalFolderLinkDTO> listLinks );

    /**
     * Validate a card
     * @param cardBean the card to validate
     * @param listChapters the chapters to validate
     * @return error key or null
     */
    String validateLocalCard( LocalCardDTO cardBean, List<LocalCardChapterDTO> listChapters );

    /**
     * Validate the root theme
     * @param folderBean the folder to validate
     * @return the theme parent title if exists, <code>null</code> otherwise
     */
    String validateRootTheme( LocalFolderDTO folderBean );

    /**
     * Validate the sibling folder
     * @param folderBean the folder to validate
     * @return error key or null
     */
    String validateLinkedFolder( LocalFolderDTO folderBean );

    /**
     * Check if parent folder is valid
     * @param cardBean the card to validate
     * @param strId the parent folder id
     * @return error key or null
     */
    String validateRootFolder( LocalCardDTO cardBean, String strId );

    /**
     * Check if a card for a link id is valid
     * @param folderBean the folder to validate
     * @param strId the card for a link id
     * @param link the card for a link
     * @return error key or null
     */
    String validateLinkCard( LocalFolderDTO folderBean, String strId, LocalFolderLinkDTO link );

    /**
     * Check if a sibling card id is valid
     * @param cardBean the card to validate
     * @param strId the sibling card id
     * @return error key or null
     */
    String validateLinkedCard( LocalCardDTO cardBean, String strId );
}
