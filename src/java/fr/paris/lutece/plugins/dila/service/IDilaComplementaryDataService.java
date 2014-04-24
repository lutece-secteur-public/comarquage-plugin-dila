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

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;

import org.w3c.dom.Document;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;


/**
 * Service for {@link ComplementaryDataDTO}
 */
public interface IDilaComplementaryDataService
{
    /**
     * Method to get all {@link ComplementaryDataDTO}
     * @return list of all {@link ComplementaryDataDTO}
     */
    List<ComplementaryDataDTO> findAll(  );

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
