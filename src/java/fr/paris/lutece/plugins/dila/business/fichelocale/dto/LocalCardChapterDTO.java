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
package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

/**
 * DTO for Chapitre Fiche Locale object
 */
public class LocalCardChapterDTO
{
    /**
     * The id of resource
     */
    private Long _lLocalCardChapterId;

    /**
     * The chapter title
     */
    private String _strTitle;

    /**
     * The chapter content
     */
    private String _strContent;

    /**
     * The position of chapter
     */
    private int _nPosition;

    /**
     * The linked {@link LocalCardDTO}
     */
    private LocalCardDTO _localCard;

    /**
     * @return the _lLocalCardChapterId
     */
    public Long getLocalCardChapterId( )
    {
        return _lLocalCardChapterId;
    }

    /**
     * @param lLocalCardChapterId the _lLocalCardChapterId to set
     */
    public void setLocalCardChapterId( Long lLocalCardChapterId )
    {
        this._lLocalCardChapterId = lLocalCardChapterId;
    }

    /**
     * @return the _strTitle
     */
    public String getTitle( )
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
     * @return the _strContent
     */
    public String getContent( )
    {
        return _strContent;
    }

    /**
     * @param strContent the _strContent to set
     */
    public void setContent( String strContent )
    {
        this._strContent = strContent;
    }

    /**
     * @return the position
     */
    public int getPosition( )
    {
        return _nPosition;
    }

    /**
     * @param nPosition the position to set
     */
    public void setPosition( int nPosition )
    {
        this._nPosition = nPosition;
    }

    /**
     * @return the _localCard
     */
    public LocalCardDTO getLocalCard( )
    {
        return _localCard;
    }

    /**
     * @param localCard the _localCard to set
     */
    public void setLocalCard( LocalCardDTO localCard )
    {
        this._localCard = localCard;
    }
}
