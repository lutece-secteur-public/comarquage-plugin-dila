package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;

import java.util.List;


/**
 * DAO for {@link ComplementaryDataDTO}
 * @author rputegnat
 */
public interface IComplementaryDataDAO
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
    Long insert( ComplementaryDataDTO dto );

    /**
     * Check if a fiche has already a complement
     * @param id the id to check
     * @return <code>true</code> if fiche has already a complement,
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
     * @param donneesComplementaires the {@link ComplementaryDataDTO} to
     *            update
     */
    void store( ComplementaryDataDTO donneesComplementaires );

    /**
     * Delete a {@link ComplementaryDataDTO}
     * @param id the id to delete
     */
    void delete( Long id );

    /**
     * Find {@link ComplementaryDataDTO} by fiche and audience
     * @param cardId fiche id
     * @param audienceId audience id
     * @return the found {@link ComplementaryDataDTO}
     */
    ComplementaryDataDTO findByCardAndAudience( Long cardId, Long audienceId );

}
