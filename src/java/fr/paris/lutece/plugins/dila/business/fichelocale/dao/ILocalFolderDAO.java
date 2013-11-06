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
package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;

import java.util.List;


/**
 * Interface DAO for {@link LocalFolderDTO}
 */
public interface ILocalFolderDAO
{
    /**
     * Create a {@link LocalFolderDTO}
     * @param folder the fodler to create
     * @return the id of new {@link LocalFolderDTO}
     */
    Long create( LocalFolderDTO folder );

    /**
     * Find folder id by its local id
     * @param localId the local id to find
     * @return the id of corresponding {@link LocalFolderDTO}
     */
    Long findFolderIdByLocalId( String localId );

    /**
     * Delete a {@link LocalFolderDTO}
     * @param localFolderId the id to delete
     */
    void delete( Long localFolderId );

    /**
     * Find a {@link LocalFolderDTO} by its local id
     * @param localId the local id to find
     * @return the corresponding local id
     */
    LocalFolderDTO findFolderByLocalId( Long localId );

    /**
     * Update a {@link LocalFolderDTO}
     * @param folder the {@link LocalFolderDTO} to update
     */
    void store( LocalFolderDTO folder );

    /**
     * Find all {@link LocalFolderDTO} attached to parent theme
     * @param parentId the parent theme id
     * @return the attached local folders
     */
    List<LocalFolderDTO> findLocalFoldersByParentTheme( String parentId );
}
