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

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;

import java.util.List;


/**
 * DAO for {@link ComplementaryDataLinkDTO}
 */
public interface IComplementaryDataLinkDAO
{
    /**
     * Create a {@link ComplementaryDataLinkDTO}
     * @param dto the {@link ComplementaryDataLinkDTO} to create
     */
    void insert( ComplementaryDataLinkDTO dto );

    /**
     * Delete all links of a complementary data
     * @param id the donnee complementaire id to find
     * @param type the type of dto to delete
     */
    void delete( Long id, ComplementaryLinkTypeEnum type );

    /**
     * Find links by data id and link type
     * @param dataId the id of complementary data
     * @param type the type of dto
     * @return list of corresponding {@link ComplementaryDataLinkDTO}
     */
    List<ComplementaryDataLinkDTO> findByDataId( Long dataId, ComplementaryLinkTypeEnum type );
}
