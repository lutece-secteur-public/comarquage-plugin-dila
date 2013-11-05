package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;

import java.util.List;


/**
 * Service for {@link LocalCardChapterDTO}
 */
public interface IDilaLocalCardChapterService
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
