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

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;

import java.util.List;


/**
 * DAO for {@link XmlDTO}
 */
public interface IXmlDAO
{
    /**
     * Find the title of a {@link XmlDTO}
     * @param id the id to find
     * @return the corresponding title
     */
    String findTitleById( String id );

    /**
     * Find a {@link XmlDTO} by its id
     * @param id the id to find
     * @return the corresponding {@link XmlDTO}
     */
    XmlDTO findFolderById( String id );

    /**
     * Find the resource type of a {@link XmlDTO}
     * @param strIdXml the xml id
     * @param lIdAudience the audience id
     * @return the corresponding resource type
     */
    String findResourceTypeByIdXMLAndAudience( String strIdXml, Long lIdAudience );

    /**
     * Find a {@link XmlDTO} by its id and type
     * @param parentThemeId the id to find
     * @param availableTypes list of types for the resource
     * @param audienceId the id audience
     * @return the corresponding title
     */
    XmlDTO findByIdAndTypesAndAudience( String parentThemeId, List<String> availableTypes, Long audienceId );

    /**
     * Find the title of a {@link XmlDTO}
     * @param siblingFolderId the id to find
     * @param availableTypes list of types for the resource
     * @param idAudience the id audience
     * @return the corresponding title
     */
    String findTitleByIdAndTypesAndAudience( String siblingFolderId, List<String> availableTypes, Long idAudience );

    /**
     * Find all XML
     * @return list of all XML files
     */
    List<XmlDTO> findAll(  );

    /**
     * Find all "Comment faire si" resources for audience
     * @param audienceId the audience to check
     * @return the list of corresponding {@link XmlDTO}
     */
    List<XmlDTO> findHowToByAudience( Long audienceId );

    /**
     * Find a national card's technical id by its xml name and audience
     * @param xmlName the xmlName
     * @param audienceId the audience id
     * @return the technical id, null if not found
     */
    Long findIdByXmlAndAudience( String xmlName, Long audienceId );

    /**
     * Find the "How to ..." section for given audience
     * @param audienceId the audienceId
     * @return {@link XmlDTO} for "How to ..." section of given audience
     */
    XmlDTO findHomeHowTo( Long audienceId );

    /**
     * Create a {@link XmlDTO}
     * @param dilaXml the xml to create
     */
    void create( XmlDTO dilaXml );

    /**
     * Update a {@link XmlDTO}
     * @param dilaXml the xml to store
     */
    void store( XmlDTO dilaXml );

    /**
     * Delete old files
     */
    void delete(  );
}
