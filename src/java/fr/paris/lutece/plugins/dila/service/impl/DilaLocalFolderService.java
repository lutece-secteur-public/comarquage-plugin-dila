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

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalFolderDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalFolderService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Implementation of {@link IDilaLocalFolderService}
 */
public class DilaLocalFolderService implements IDilaLocalFolderService, Serializable
{
    private static final String SEQ_ATTRIBUTE = "seq";
    private static final String TITLE_TAG = "Titre";
    private static final String ID_ATTRIBUTE = "ID";
    private static final String FOLDER_TAG = "Dossier";

    /** The serial ID */
    private static final long serialVersionUID = -8207832026211506607L;
    @Inject
    @Named( "dilaLocalFolderDAO" )
    private ILocalFolderDAO _dilaLocalFolderDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create( LocalFolderDTO dossier )
    {
        return _dilaLocalFolderDAO.create( dossier );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findFolderIdByLocalId( String idLocal )
    {
        return _dilaLocalFolderDAO.findFolderIdByLocalId( idLocal );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( Long localFolderId )
    {
        _dilaLocalFolderDAO.delete( localFolderId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalFolderDTO findFolderByLocalId( Long idLocal )
    {
        return _dilaLocalFolderDAO.findFolderByLocalId( idLocal );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( LocalFolderDTO folder )
    {
        _dilaLocalFolderDAO.store( folder );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document insertFolderLinks( String parentId, DocumentBuilder builder, Document document )
    {
        List<LocalFolderDTO> folderList = this.findLocalFoldersByParentTheme( parentId );

        for ( LocalFolderDTO currentFolder : folderList )
        {
            NodeList folders = document.getElementsByTagName( FOLDER_TAG );
            Element newFolder = document.createElement( FOLDER_TAG );
            newFolder.setAttribute( ID_ATTRIBUTE, currentFolder.getLocalDTO( ).getId( ).toString( ) );

            Element newTitle = document.createElement( TITLE_TAG );
            newTitle.setTextContent( currentFolder.getLocalDTO( ).getTitle( ) );
            newFolder.appendChild( newTitle );

            boolean hasToShift = false;

            for ( int i = 0; i < folders.getLength( ); i++ )
            {
                Element folderElement = (Element) folders.item( i );
                String seq = folderElement.getAttribute( SEQ_ATTRIBUTE );

                if ( folderElement.getAttribute( ID_ATTRIBUTE ).equals( currentFolder.getSiblingFolderId( ) ) )
                {
                    if ( currentFolder.getPosition( ) == 1 )
                    {
                        if ( seq != null )
                        {
                            newFolder.setAttribute( SEQ_ATTRIBUTE, "" + Integer.parseInt( seq ) );
                            folderElement.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                        }

                        folderElement.getParentNode( ).insertBefore( newFolder, folderElement );
                    }
                    else
                    {
                        if ( seq != null )
                        {
                            newFolder.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                        }

                        folderElement.getParentNode( ).insertBefore( newFolder, folderElement.getNextSibling( ) );
                    }

                    i++;
                    hasToShift = true;
                }
                else if ( hasToShift && ( seq != null ) )
                {
                    folderElement.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                }
            }
            //if folder was not added, we add it at the end
            if ( !hasToShift )
            {
                Element folderElement = (Element) folders.item( folders.getLength( ) - 1 );
                folderElement.getParentNode( ).insertBefore( newFolder, null );
            }

            document.getDocumentElement( ).normalize( );
        }

        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalFolderDTO> findLocalFoldersByParentTheme( String idParent )
    {
        return _dilaLocalFolderDAO.findLocalFoldersByParentTheme( idParent );
    }
}
