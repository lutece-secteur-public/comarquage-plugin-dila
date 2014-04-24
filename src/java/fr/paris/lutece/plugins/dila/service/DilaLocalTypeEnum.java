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

/**
 * Eunm for type resource
 */
public enum DilaLocalTypeEnum
{
    CARD( 1L, "Fiche" ), FOLDER( 2L, "Dossier" );

    /**
     * The enum id
     */
    private Long _lId;

    /**
     * The enum label
     */
    private String _strLabel;

    /**
     * Constructor
     * @param lId the id
     * @param strLabel the label
     */
    private DilaLocalTypeEnum( Long lId, String strLabel )
    {
        _lId = lId;
        _strLabel = strLabel;
    }

    /**
     * @return the _lId
     */
    public Long getId( )
    {
        return _lId;
    }

    /**
     * @return the _strLabel
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * Get the enum from its id
     * @param lId the id to search
     * @return the corresponding enum
     */
    public static DilaLocalTypeEnum fromId( Long lId )
    {
        for ( DilaLocalTypeEnum e : DilaLocalTypeEnum.values( ) )
        {
            if ( e.getId( ).compareTo( lId ) == 0 )
            {
                return e;
            }
        }

        return null;
    }
}
