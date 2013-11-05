package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;

import java.util.List;


/**
 * DAO for {@link LocalCardDTO}
 */
public interface ILocalCardDAO
{
    /**
     * Create a {@link LocalCardDTO}
     * @param card the card to create
     * @return the id of new card
     */
    Long insert( LocalCardDTO card );

    /**
     * Find a {@link LocalCardDTO} id by its local id
     * @param localId the id to find
     * @return the id of corresponding {@link LocalCardDTO}
     */
    Long findCardIdByLocalId( String localId );

    /**
     * Delete a card
     * @param localCardId the id of card to delete
     */
    void delete( Long localCardId );

    /**
     * Find a {@link LocalCardDTO} by its id local
     * @param localId the id to find
     * @return the corresponding {@link LocalCardDTO}
     */
    LocalCardDTO findCardByLocalId( Long localId );

    /**
     * Update a {@link LocalCardDTO}
     * @param card the card to update
     */
    void store( LocalCardDTO card );

    /**
     * Check if a {@link LocalCardDTO} reference the idLocal as parent dossier
     * @param localId the id to check
     * @return <code>true</code> if at least one card exists, <code>false</code>
     *         otherwise
     */
    boolean isCardWithParentId( String localId );

    /**
     * Find all {@link LocalCardDTO} attached to parent folder
     * @param parentId the parent folder id
     * @return the attached local cards
     */
    List<LocalCardDTO> findLocalCardsByParentFolder( String parentId );
}
