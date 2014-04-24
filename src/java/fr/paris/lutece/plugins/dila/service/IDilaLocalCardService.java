/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;

import org.w3c.dom.Document;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;


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
