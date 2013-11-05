package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;

import java.util.List;


/**
 * DAO for {@link LocalDTO}
 */
public interface ILocalDAO
{
    /**
     * Find a {@link LocalDTO} by its id and type
     * @param id the id to find
     * @param type the type
     * @param idAudience the id audience
     * @return the corresponding {@link LocalDTO}
     */
    LocalDTO findLocalByIdAndTypeAndAudience( Long id, Long type, Long idAudience );

    /**
     * Find a title by its id and type
     * @param id the id to find
     * @param type the type
     * @param idAudience the id audience
     * @return the title of corresponding {@link LocalDTO}
     */
    String findTitleByIdAndTypeAndAudience( Long id, Long type, Long idAudience );

    /**
     * Find all {@link LocalDTO}
     * @return list of all {@link LocalDTO}
     */
    List<LocalDTO> findAll( );

    /**
     * Create a {@link LocalDTO}
     * @param local the {@link LocalDTO} to create
     * @param addIdToBreadcrumb <code>true</code> if id must be id to
     *            breadcrumb, <code>false</code> otherwise
     * @return the id of the new {@link LocalDTO}
     */
    Long insert( LocalDTO local, boolean addIdToBreadcrumb );

    /**
     * Delete a {@link LocalDTO}
     * @param idLocal the id to delete
     */
    void delete( String idLocal );

    /**
     * Update a {@link LocalDTO}
     * @param localDTO the {@link LocalDTO} to update
     * @param addIdToBreadcrumb <code>true</code> if id must be id to
     *            breadcrumb, <code>false</code> otherwise
     */
    void store( LocalDTO localDTO, boolean addIdToBreadcrumb );

    /**
     * Find xml content of {@link LocalDTO} by its id
     * @param id the id
     * @return the xml
     */
    String findXmlById( Long id );

    /**
     * Find 10 last fiches for display
     * @param categoryId the audience to check
     * @return list of corresponding {@link LocalDTO}
     */
    List<LocalDTO> findLastCardsByAudience( Long categoryId );

    /**
     * Find all {@link LocalDTO} by audience id
     * @param audienceId the audience id
     * @return all {@link LocalDTO} in audience id
     */
    List<LocalDTO> findAllByAudienceId( Long audienceId );
}
