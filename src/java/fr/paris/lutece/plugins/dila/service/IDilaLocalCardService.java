package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;


/**
 * Interface For {@link LocalCardDTO}
 */
public interface IDilaLocalCardService
{
    /**
     * Create a {@link LocalCardDTO}
     * @param card the {@link LocalCardDTO} to create
     * @return the id of new card
     */
    Long create( LocalCardDTO card );

    /**
     * Find a card id by its local id
     * @param localId the id find
     * @return the corresponding id
     */
    Long findCardIdByLocalId( String localId );

    /**
     * Delete a {@link LocalCardDTO}
     * @param localCardId the id to delete
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
    void update( LocalCardDTO card );

    /**
     * Check if a {@link LocalCardDTO} reference the idLocal as parent folder
     * @param localId the id to check
     * @return <code>true</code> if at least one fiche exists,
     *         <code>false</code> otherwise
     */
    boolean isCardWithParentId( String localId );

    /**
     * Insert local links into document
     * @param parentId the parent id
     * @param builder the builder to use
     * @param document the document to complete
     * @return the document with links
     */
    Document insertCardLinks( String parentId, DocumentBuilder builder, Document document );

    /**
     * Find all {@link LocalCardDTO} attached to parent folder
     * @param parentId the parent folder id
     * @return the attached local cards
     */
    List<LocalCardDTO> findLocalCardsByParentFolder( String parentId );
}
