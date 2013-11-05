package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;


/**
 * Service for {@link ComplementaryDataDTO}
 */
public interface IDilaComplementaryDataService
{

    /**
     * Method to get all {@link ComplementaryDataDTO}
     * @return list of all {@link ComplementaryDataDTO}
     */
    List<ComplementaryDataDTO> findAll( );

    /**
     * Create a new {@link ComplementaryDataDTO}
     * @param dto the {@link ComplementaryDataDTO} to create
     * @return the id of new {@link ComplementaryDataDTO}
     */
    Long create( ComplementaryDataDTO dto );

    /**
     * Check if a card already has a complement
     * @param id the id to check
     * @return <code>true</code> if card already has a complement,
     *         <code>false</code> otherwise
     */
    boolean cardHasComplement( String id );

    /**
     * Find a {@link ComplementaryDataDTO} by its id
     * @param id the id to find
     * @return the corresponding {@link ComplementaryDataDTO}
     */
    ComplementaryDataDTO findById( Long id );

    /**
     * Update a {@link ComplementaryDataDTO}
     * @param complementaryData the {@link ComplementaryDataDTO} to
     *            update
     */
    void update( ComplementaryDataDTO complementaryData );

    /**
     * Delete a {@link ComplementaryDataDTO}
     * @param id the id to delete
     */
    void delete( Long id );

    /**
     * Insert complementary data in given fiche
     * @param cardTechnicalId the card to complete
     * @param categoryId the categoy id
     * @param builder the builder
     * @param document the document to complete
     * @return the completed document
     */
    Document insertComplementaryData( Long cardTechnicalId, Long categoryId, DocumentBuilder builder, Document document );

    /**
     * Find {@link ComplementaryDataDTO} by card and audience
     * @param cardId fiche id
     * @param audienceId audience id
     * @return the found {@link ComplementaryDataDTO}
     */
    ComplementaryDataDTO findByCardAndAudience( Long cardId, Long audienceId );
}
