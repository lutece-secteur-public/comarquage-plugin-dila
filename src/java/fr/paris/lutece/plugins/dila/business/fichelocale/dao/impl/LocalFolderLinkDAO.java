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
package fr.paris.lutece.plugins.dila.business.fichelocale.dao.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalFolderLinkDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link ILocalFolderLinkDAO}
 */
public class LocalFolderLinkDAO implements ILocalFolderLinkDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 3635002841395718887L;
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_local_folder_link";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_local_folder_link "
            + "( id, title, position, card_id, local_folder_id ) VALUES ( ?, ? ,?, ?, ?)";
    private static final String SQL_QUERY_DELETE_BY_FOLDER_ID = " DELETE FROM dila_local_folder_link "
            + "WHERE local_folder_id = ?";
    private static final String SQL_QUERY_SELECT_BY_FOLDER_ID = " SELECT title, position, card_id FROM dila_local_folder_link "
            + "WHERE local_folder_id = ? ORDER BY position";

    /**
     * Generates a new primary key
     * @return The new primary key
     */
    private Long newPrimaryKey( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.executeQuery( );

        Long nKey = 1L;

        if ( daoUtil.next( ) )
        {
            // if the table is empty
            nKey = daoUtil.getLong( 1 ) + 1L;
        }

        daoUtil.free( );

        return nKey;
    }

    @Override
    public void insert( LocalFolderLinkDTO link )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        link.setId( newPrimaryKey( ) );

        daoUtil.setLong( 1, link.getId( ) );
        daoUtil.setString( 2, link.getTitle( ) );
        daoUtil.setInt( 3, link.getPosition( ) );
        daoUtil.setString( 4, link.getCardId( ) );
        daoUtil.setLong( 5, link.getLocalFolderId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void deleteByFolderId( Long localFolderId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_FOLDER_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, localFolderId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public List<LocalFolderLinkDTO> findByFolderId( Long id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_FOLDER_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setLong( 1, id );
        daoUtil.executeQuery( );

        List<LocalFolderLinkDTO> result = new ArrayList<LocalFolderLinkDTO>( );

        while ( daoUtil.next( ) )
        {
            LocalFolderLinkDTO link = new LocalFolderLinkDTO( );

            link.setTitle( daoUtil.getString( 1 ) );
            link.setPosition( daoUtil.getInt( 2 ) );
            link.setCardId( daoUtil.getString( 3 ) );

            result.add( link );
        }

        daoUtil.free( );

        return result;
    }
}
