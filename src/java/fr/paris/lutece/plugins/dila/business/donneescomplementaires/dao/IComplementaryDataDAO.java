/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;

import java.util.List;


/**
 * DAO for {@link ComplementaryDataDTO}
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
