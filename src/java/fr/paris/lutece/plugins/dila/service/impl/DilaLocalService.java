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
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import javax.xml.parsers.DocumentBuilder;


/**
 * Implementation of {@link IDilaLocalService}
 */
public class DilaLocalService implements IDilaLocalService, Serializable
{
    private static final String ID_ATTRIBUTE = "ID";
    private static final String CARD_TAG = "Fiche";

    /** The serial ID */
    private static final long serialVersionUID = -9211044575927578839L;
    @Inject
    @Named( "dilaLocalDAO" )
    private ILocalDAO _dilaLocalDAO;

    @Override
    public LocalDTO findLocalByIdAndTypeAndAudience( Long id, Long type, Long idAudience )
    {
        return _dilaLocalDAO.findLocalByIdAndTypeAndAudience( id, type, idAudience );
    }

    @Override
    public String findTitleByIdAndTypeAndAudience( Long id, Long type, Long idAudience )
    {
        return _dilaLocalDAO.findTitleByIdAndTypeAndAudience( id, type, idAudience );
    }

    @Override
    public List<LocalDTO> findAll(  )
    {
        return _dilaLocalDAO.findAll(  );
    }

    @Override
    public Long create( LocalDTO local, boolean addIdToBreadcrumb )
    {
        return _dilaLocalDAO.insert( local, addIdToBreadcrumb );
    }

    @Override
    public void delete( String idLocal )
    {
        _dilaLocalDAO.delete( idLocal );
    }

    @Override
    public void update( LocalDTO localDTO, boolean addIdToBreadcrumb )
    {
        _dilaLocalDAO.store( localDTO, addIdToBreadcrumb );
    }

    @Override
    public String findXmlById( Long id )
    {
        return _dilaLocalDAO.findXmlById( id );
    }

    @Override
    public Document insertLastCardsLinks( Long categoryId, DocumentBuilder builder, Document document )
    {
        List<LocalDTO> localList = this.findLastCardsByAudience( categoryId );

        for ( LocalDTO currentLocal : localList )
        {
            Element newCard = document.createElement( CARD_TAG );
            newCard.setAttribute( ID_ATTRIBUTE, currentLocal.getId(  ).toString(  ) );
            newCard.setTextContent( currentLocal.getTitle(  ) );
            document.getDocumentElement(  ).appendChild( newCard );
            document.getDocumentElement(  ).normalize(  );
        }

        return document;
    }

    /**
     * Return last locale cards by audience
     * @param lAudienceId the audience to check
     * @return the list of cards
     */
    private List<LocalDTO> findLastCardsByAudience( Long lAudienceId )
    {
        return _dilaLocalDAO.findLastCardsByAudience( lAudienceId );
    }

    @Override
    public List<LocalDTO> findAllByAudienceId( Long audienceId )
    {
        return _dilaLocalDAO.findAllByAudienceId( audienceId );
    }
}
