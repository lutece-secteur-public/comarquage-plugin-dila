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
    List<LocalDTO> findAll(  );

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
