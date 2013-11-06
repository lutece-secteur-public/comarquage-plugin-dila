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
package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto;

import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;


/**
 * DTO for dila_ donnees_complementaires table
 */
public abstract class ComplementaryDataLinkDTO
{
    /**
     * The database id
     */
    private Long _lId;

    /**
     * The title
     */
    private String _strTitle;

    /**
     * The url
     */
    private String _strURL;

    /**
     * The position
     */
    private Integer _nPosition;

    /**
     * The id to linked complementary data
     */
    private Long _lIdComplementaryData;

    /**
     * @return the _lId
     */
    public Long getId(  )
    {
        return _lId;
    }

    /**
     * @param lId the _lId to set
     */
    public void setId( Long lId )
    {
        this._lId = lId;
    }

    /**
     * @return the _strTitle
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * @param strTitle the _strTitle to set
     */
    public void setTitle( String strTitle )
    {
        this._strTitle = strTitle;
    }

    /**
     * @return the _strURL
     */
    public String getURL(  )
    {
        return _strURL;
    }

    /**
     * @param strURL the _strURL to set
     */
    public void setURL( String strURL )
    {
        this._strURL = strURL;
    }

    /**
     * @return the _nPosition
     */
    public Integer getPosition(  )
    {
        return _nPosition;
    }

    /**
     * @param nPosition the _nPosition to set
     */
    public void setPosition( Integer nPosition )
    {
        this._nPosition = nPosition;
    }

    /**
     * @return the _lIdComplementaryData
     */
    public Long getIdComplementaryData(  )
    {
        return _lIdComplementaryData;
    }

    /**
     * @param lIdComplementaryData the _lIdComplementaryData to set
     */
    public void setIdComplementaryData( Long lIdComplementaryData )
    {
        this._lIdComplementaryData = lIdComplementaryData;
    }

    /**
     * @return the type of link
     */
    public abstract ComplementaryLinkTypeEnum getType(  );
}
