package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;

import java.util.List;


/**
 * DAO for {@link LocalCardChapterDTO}
 * @author rputegnat
 */
public interface ILocalCardChapterDAO
{
    /**
     * Create a chapter
     * @param chapter the chapter to create
     */
    void create( LocalCardChapterDTO chapter );

    /**
     * Delete chapters by card id
     * @param localCardId the card id to find
     */
    void deleteByCardId( Long localCardId );

    /**
     * Find {@link List} of {@link LocalCardChapterDTO} by card id
     * @param cardId card id to find
     * @return {@link List} of corresponding {@link LocalCardChapterDTO}
     */
    List<LocalCardChapterDTO> findByCardId( Long cardId );

}
