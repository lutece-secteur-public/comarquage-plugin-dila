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

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalFolderDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link ILocalFolderDAO}
 */
public class LocalFolderDAO implements ILocalFolderDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -4915475113278829044L;

    // QUERIES
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_local_folder";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_local_folder "
            + "( id, parent_theme_id, sibling_folder_id, position, presentation, local_id ) "
            + " VALUES ( ?, ? ,?, ?, ?, ? )";
    private static final String SQL_QUERY_SELECT_FOLDER_ID_BY_LOCAL_ID = "SELECT "
            + "id FROM dila_local_folder WHERE local_id = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM dila_local_folder WHERE id = ?";
    private static final String SQL_QUERY_SELECT_FOLDER_BY_LOCAL_ID = "SELECT "
            + "dossier.id, dossier.parent_theme_id, dossier.sibling_folder_id, dossier.position, dossier.presentation, local.title, local.author, local.audience_id "
            + "FROM dila_local_folder dossier, dila_local local "
            + "WHERE dossier.local_id = ? AND dossier.local_id = local.id";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_local_folder"
            + " SET parent_theme_id = ?, sibling_folder_id = ?, position = ?, presentation = ?" + " WHERE id = ?";
    private static final String SQL_QUERY_FIND_FOLDERS_BY_PARENT_ID = "SELECT l.id, l.title, d.sibling_folder_id, d.position"
            + " FROM dila_local_folder d JOIN dila_local l ON d.local_id = l.id" + " WHERE d.parent_theme_id = ?";

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
    public Long create( LocalFolderDTO folder )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        folder.setId( newPrimaryKey( ) );

        daoUtil.setLong( 1, folder.getId( ) );
        daoUtil.setString( 2, folder.getParentThemeId( ) );
        daoUtil.setString( 3, folder.getSiblingFolderId( ) );

        if ( folder.getPosition( ) != null )
        {
            daoUtil.setInt( 4, folder.getPosition( ) );
        }
        else
        {
            daoUtil.setIntNull( 4 );
        }

        daoUtil.setString( 5, folder.getPresentation( ) );
        daoUtil.setLong( 6, folder.getLocalDTO( ).getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );

        return folder.getId( );
    }

    @Override
    public Long findFolderIdByLocalId( String localId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_FOLDER_ID_BY_LOCAL_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, localId );
        daoUtil.executeQuery( );

        Long result = null;

        if ( daoUtil.next( ) )
        {
            result = daoUtil.getLong( 1 );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public void delete( Long dossierLocalId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, dossierLocalId );
        daoUtil.executeUpdate( );

        daoUtil.free( );
    }

    @Override
    public LocalFolderDTO findFolderByLocalId( Long localId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_FOLDER_BY_LOCAL_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, localId );
        daoUtil.executeQuery( );

        LocalFolderDTO folder = null;

        if ( daoUtil.next( ) )
        {
            folder = new LocalFolderDTO( );
            folder.getLocalDTO( ).setId( localId );

            folder.setId( daoUtil.getLong( 1 ) );
            folder.setParentThemeId( daoUtil.getString( 2 ) );

            if ( daoUtil.getString( 3 ) != null )
            {
                folder.setSiblingFolderId( daoUtil.getString( 3 ) );
                folder.setPosition( daoUtil.getInt( 4 ) );
            }

            folder.setPresentation( daoUtil.getString( 5 ) );

            folder.getLocalDTO( ).setTitle( daoUtil.getString( 6 ) );
            folder.getLocalDTO( ).setAuthor( daoUtil.getString( 7 ) );
            folder.getLocalDTO( ).setIdAudience( daoUtil.getLong( 8 ) );
        }

        daoUtil.free( );

        return folder;
    }

    @Override
    public void store( LocalFolderDTO folder )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setString( 1, folder.getParentThemeId( ) );
        daoUtil.setString( 2, folder.getSiblingFolderId( ) );

        if ( folder.getPosition( ) != null )
        {
            daoUtil.setInt( 3, folder.getPosition( ) );
        }
        else
        {
            daoUtil.setIntNull( 3 );
        }

        daoUtil.setString( 4, folder.getPresentation( ) );
        daoUtil.setLong( 5, folder.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public List<LocalFolderDTO> findLocalFoldersByParentTheme( String parentId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_FOLDERS_BY_PARENT_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, parentId );
        daoUtil.executeQuery( );

        List<LocalFolderDTO> resultList = new ArrayList<LocalFolderDTO>( );

        while ( daoUtil.next( ) )
        {
            LocalFolderDTO localFolder = new LocalFolderDTO( );
            LocalDTO local = new LocalDTO( );
            local.setId( daoUtil.getLong( 1 ) );
            local.setTitle( daoUtil.getString( 2 ) );
            localFolder.setLocalDTO( local );
            localFolder.setSiblingFolderId( daoUtil.getString( 3 ) );
            localFolder.setPosition( daoUtil.getInt( 4 ) );
            localFolder.setParentThemeId( parentId );
            resultList.add( localFolder );
        }

        daoUtil.free( );

        return resultList;
    }
}
