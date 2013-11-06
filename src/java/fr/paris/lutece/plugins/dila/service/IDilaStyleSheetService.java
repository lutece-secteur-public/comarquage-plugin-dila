/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.plugins.dila.business.stylesheet.dto.DilaStyleSheet;

import java.util.List;


/**
 * The Interface IDilaStyleSheetService.
 */
public interface IDilaStyleSheetService
{
    /**
     * Returns a collection of Dila StyleSheet object
     *
     * @param nTypeContenuId The typeContenu identifier
     * @param strStyleSheetName The Dila StyleSheet name
     * @return A list of Dila StyleSheet object
     */
    List<DilaStyleSheet> getDilaStyleSheetList( Integer nTypeContenuId, String strStyleSheetName );

    /**
     * Returns the number of dila stylesheets associated to the typeContenu
     * specified in parameter
     *
     * @param nTypeContenuId the typeContenu id
     * @return the number of dila stylesheet associated
     */
    Integer getStyleSheetNbPerTypeContenu( int nTypeContenuId );

    /**
     * Creation of an instance of a Dila Stylesheet file in the database
     *
     * @param stylesheet An instance of a dila stylesheet which contains the
     *            informations to store
     */
    void create( DilaStyleSheet stylesheet );

    /**
     * Return the dila style sheet by his id
     *
     * @param nIdStyleSheet the stylesheet id
     * @return DilaStyleSheet
     */
    DilaStyleSheet findByPrimaryKey( Integer nIdStyleSheet );

    /**
     * Update the dila stylesheet
     *
     * @param stylesheet the stylesheet
     */
    void update( DilaStyleSheet stylesheet );

    /**
     * Delete a styleSheet.
     *
     * @param nIdStyleSheet this "dilaStyleSheet" id
     */
    void doDeleteStyleSheet( Integer nIdStyleSheet );

    /**
     * Return the source for specific stylesheet
     *
     * @param nIdStyleSheet stylesheet id
     * @return byte[] source
     */
    byte[] getSourceByStyleSheetId( Integer nIdStyleSheet );
}
