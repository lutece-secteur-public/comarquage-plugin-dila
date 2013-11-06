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
package fr.paris.lutece.plugins.dila.business.enums;


/**
 * Enum for all resource types
 */
public enum ResourceTypeEnum
{CARD( "Fiche", 1L ),
    RESOURCE( "Ressource", 3L ),
    Q_AND_A( "Question-réponse", 2L ),
    FOLDER( "Dossier", 2L ),
    SUBTHEME( "Sous-thème", 2L ),
    HOW_TO( "Comment faire si", 2L ),
    THEME( "Thème", 2L ),
    HOW_TO_HOME( "Accueil Comment faire si", 2L );

    /**
     * the type resource label
     */
    private String _strLabel;

    /**
     * the content type
     */
    private Long _lContentType;

    /**
     * Constructor
     * @param strLabel the type resource label
     * @param lContentType the content type
     */
    private ResourceTypeEnum( String strLabel, Long lContentType )
    {
        _strLabel = strLabel;
        _lContentType = lContentType;
    }

    /**
     * Get enum from label
     * @param strLabel label to find
     * @return the corresponding {@link ResourceTypeEnum} or null
     */
    public static ResourceTypeEnum fromLabel( String strLabel )
    {
        for ( ResourceTypeEnum e : ResourceTypeEnum.values(  ) )
        {
            if ( e.getLabel(  ).equals( strLabel ) )
            {
                return e;
            }
        }

        return null;
    }

    /**
     *
     * @return _strLabel the label
     */
    public String getLabel(  )
    {
        return _strLabel;
    }

    /**
     *
     * @return _lContentType the content type
     */
    public Long getContentType(  )
    {
        return _lContentType;
    }
}
