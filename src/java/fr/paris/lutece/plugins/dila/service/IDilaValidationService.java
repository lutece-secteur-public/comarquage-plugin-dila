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

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;

import java.util.List;


/**
 * Service for validation
 */
public interface IDilaValidationService
{
    /**
     * Validate a folder
     * @param folderBean the folder to validate
     * @param listLinks the links to validate
     * @return error key or null
     */
    String validateLocalFolder( LocalFolderDTO folderBean, List<LocalFolderLinkDTO> listLinks );

    /**
     * Validate a card
     * @param cardBean the card to validate
     * @param listChapters the chapters to validate
     * @return error key or null
     */
    String validateLocalCard( LocalCardDTO cardBean, List<LocalCardChapterDTO> listChapters );

    /**
     * Validate the root theme
     * @param folderBean the folder to validate
     * @return the theme parent title if exists, <code>null</code> otherwise
     */
    String validateRootTheme( LocalFolderDTO folderBean );

    /**
     * Validate the sibling folder
     * @param folderBean the folder to validate
     * @return error key or null
     */
    String validateLinkedFolder( LocalFolderDTO folderBean );

    /**
     * Check if parent folder is valid
     * @param cardBean the card to validate
     * @param strId the parent folder id
     * @return error key or null
     */
    String validateRootFolder( LocalCardDTO cardBean, String strId );

    /**
     * Check if a card for a link id is valid
     * @param folderBean the folder to validate
     * @param strId the card for a link id
     * @param link the card for a link
     * @return error key or null
     */
    String validateLinkCard( LocalFolderDTO folderBean, String strId, LocalFolderLinkDTO link );

    /**
     * Check if a sibling card id is valid
     * @param cardBean the card to validate
     * @param strId the sibling card id
     * @return error key or null
     */
    String validateLinkedCard( LocalCardDTO cardBean, String strId );
}
