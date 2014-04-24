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
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.IComplementaryDataDAO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataLinkService;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import javax.xml.parsers.DocumentBuilder;


/**
 * Implementation of {@link IDilaComplementaryDataService}
 */
public class DilaComplementaryDataService implements IDilaComplementaryDataService, Serializable
{
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String PROPERTY_TELESERVICE = "Téléservice";
    private static final String TELESERVICE_TAG = "ServiceEnLigne";
    private static final String TITLE_TAG = "Titre";
    private static final String SEQ_ATTRIBUTE = "seq";
    private static final String URL_ATTRIBUTE = "URL";
    private static final String LEARN_MORE_TAG = "PourEnSavoirPlus";
    private static final String COLUMN_BLOCK_TAG = "BlocColonne";
    private static final String BOTTOM_BLOCK_TAG = "BlocBas";

    /** The serial ID */
    private static final long serialVersionUID = 353073235359709773L;
    @Inject
    @Named( "dilaComplementaryDataDAO" )
    private IComplementaryDataDAO _dilaComplementaryDataDAO;
    @Inject
    @Named( "dilaComplementaryDataLinkService" )
    private IDilaComplementaryDataLinkService _dilaComplementaryDataLinkService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ComplementaryDataDTO> findAll(  )
    {
        return _dilaComplementaryDataDAO.findAll(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create( ComplementaryDataDTO dto )
    {
        return _dilaComplementaryDataDAO.insert( dto );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cardHasComplement( String id )
    {
        return _dilaComplementaryDataDAO.cardHasComplement( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComplementaryDataDTO findById( Long id )
    {
        return _dilaComplementaryDataDAO.findById( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( ComplementaryDataDTO complementaryData )
    {
        _dilaComplementaryDataDAO.store( complementaryData );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( Long id )
    {
        _dilaComplementaryDataDAO.delete( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document insertComplementaryData( Long cardId, Long categoryId, DocumentBuilder builder, Document document )
    {
        ComplementaryDataDTO complementaryDataDTO = this.findByCardAndAudience( cardId, categoryId );

        if ( complementaryDataDTO != null )
        {
            if ( StringUtils.isNotBlank( complementaryDataDTO.getBottomBlock(  ) ) )
            {
                Element bottomBlock = document.createElement( BOTTOM_BLOCK_TAG );
                bottomBlock.setTextContent( complementaryDataDTO.getBottomBlock(  ) );
                document.getDocumentElement(  ).appendChild( bottomBlock );
                document.getDocumentElement(  ).normalize(  );
            }

            if ( StringUtils.isNotBlank( complementaryDataDTO.getColumnBlock(  ) ) )
            {
                Element columnBlock = document.createElement( COLUMN_BLOCK_TAG );
                columnBlock.setTextContent( complementaryDataDTO.getColumnBlock(  ) );
                document.getDocumentElement(  ).appendChild( columnBlock );
                document.getDocumentElement(  ).normalize(  );
            }

            List<ComplementaryDataLinkDTO> listLearnMoreLinks = _dilaComplementaryDataLinkService.findByDataId( complementaryDataDTO.getId(  ),
                    ComplementaryLinkTypeEnum.LEARN_MORE );

            if ( CollectionUtils.isNotEmpty( listLearnMoreLinks ) )
            {
                for ( ComplementaryDataLinkDTO currentLink : listLearnMoreLinks )
                {
                    Element currentElement = document.createElement( LEARN_MORE_TAG );
                    currentElement.setAttribute( URL_ATTRIBUTE, currentLink.getURL(  ) );
                    currentElement.setAttribute( SEQ_ATTRIBUTE, currentLink.getPosition(  ).toString(  ) );

                    Element currentTitle = document.createElement( TITLE_TAG );
                    currentTitle.setTextContent( currentLink.getTitle(  ) );
                    currentElement.appendChild( currentTitle );
                    document.getDocumentElement(  ).appendChild( currentElement );
                    document.getDocumentElement(  ).normalize(  );
                }
            }

            List<ComplementaryDataLinkDTO> listTeleserviceLinks = _dilaComplementaryDataLinkService.findByDataId( complementaryDataDTO.getId(  ),
                    ComplementaryLinkTypeEnum.TELESERVICE );

            if ( CollectionUtils.isNotEmpty( listTeleserviceLinks ) )
            {
                for ( ComplementaryDataLinkDTO currentLink : listTeleserviceLinks )
                {
                    Element currentElement = document.createElement( TELESERVICE_TAG );
                    currentElement.setAttribute( URL_ATTRIBUTE, currentLink.getURL(  ) );
                    currentElement.setAttribute( SEQ_ATTRIBUTE, currentLink.getPosition(  ).toString(  ) );
                    currentElement.setAttribute( TYPE_ATTRIBUTE, PROPERTY_TELESERVICE );
                    currentElement.setTextContent( currentLink.getTitle(  ) );
                    document.getDocumentElement(  ).appendChild( currentElement );
                    document.getDocumentElement(  ).normalize(  );
                }
            }
        }

        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComplementaryDataDTO findByCardAndAudience( Long ficheId, Long audienceId )
    {
        return _dilaComplementaryDataDAO.findByCardAndAudience( ficheId, audienceId );
    }
}
