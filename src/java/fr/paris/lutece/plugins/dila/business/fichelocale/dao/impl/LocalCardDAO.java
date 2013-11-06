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
package fr.paris.lutece.plugins.dila.business.fichelocale.dao.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalCardDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implements {@link ILocalCardDAO}
 */
public class LocalCardDAO implements ILocalCardDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 1687246174844997810L;

    // QUERIES
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_local_card";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_local_card "
            + "( id, parent_folder_id, sibling_card_id, position, local_id ) " + " VALUES ( ?, ? ,?, ?, ?)";
    private static final String SQL_QUERY_SELECT_CARD_ID_BY_LOCAL_ID = "SELECT "
            + "id  FROM dila_local_card WHERE local_id = ?";
    private static final String SQL_QUERY_SELECT_CARD_BY_LOCAL_ID = "SELECT "
            + "fiche.id, fiche.parent_folder_id, fiche.sibling_card_id, fiche.position, local.title, local.author, local.audience_id FROM dila_local_card fiche, dila_local local "
            + "WHERE fiche.local_id = ? AND fiche.local_id = local.id";
    private static final String SQL_QUERY_DELETE = "DELETE FROM dila_local_card WHERE id = ?";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_local_card "
            + "set parent_folder_id = ?, sibling_card_id = ?, position = ?  WHERE id = ?";
    private static final String SQL_QUERY_SELECT_CARD_ID_BY_PARENT_ID = "SELECT "
            + "id FROM dila_local_card WHERE parent_folder_id = ?";
    private static final String SQL_QUERY_FIND_CARDS_BY_PARENT_ID = "SELECT l.id, l.title, f.sibling_card_id, f.position"
            + " FROM dila_local_card f JOIN dila_local l ON f.local_id = l.id" + " WHERE f.parent_folder_id = ?";

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
    public Long insert( LocalCardDTO card )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        card.setId( newPrimaryKey( ) );

        daoUtil.setLong( 1, card.getId( ) );
        daoUtil.setString( 2, card.getParentFolderId( ) );
        daoUtil.setString( 3, card.getSiblingCardId( ) );

        if ( card.getPosition( ) != null )
        {
            daoUtil.setInt( 4, card.getPosition( ) );
        }
        else
        {
            daoUtil.setIntNull( 4 );
        }

        daoUtil.setLong( 5, card.getLocalDTO( ).getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );

        return card.getId( );
    }

    @Override
    public Long findCardIdByLocalId( String localId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CARD_ID_BY_LOCAL_ID,
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
    public void delete( Long ficheLocalId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, ficheLocalId );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public LocalCardDTO findCardByLocalId( Long idLocal )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CARD_BY_LOCAL_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, idLocal );
        daoUtil.executeQuery( );

        LocalCardDTO card = null;

        if ( daoUtil.next( ) )
        {
            card = new LocalCardDTO( );
            card.getLocalDTO( ).setId( idLocal );
            card.setId( daoUtil.getLong( 1 ) );
            card.setParentFolderId( daoUtil.getString( 2 ) );

            if ( daoUtil.getString( 3 ) != null )
            {
                card.setSiblingCardId( daoUtil.getString( 3 ) );
                card.setPosition( daoUtil.getInt( 4 ) );
            }

            card.getLocalDTO( ).setTitle( daoUtil.getString( 5 ) );
            card.getLocalDTO( ).setAuthor( daoUtil.getString( 6 ) );
            card.getLocalDTO( ).setIdAudience( daoUtil.getLong( 7 ) );
        }

        daoUtil.free( );

        return card;
    }

    @Override
    public void store( LocalCardDTO card )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, card.getParentFolderId( ) );
        daoUtil.setString( 2, card.getSiblingCardId( ) );

        if ( card.getPosition( ) != null )
        {
            daoUtil.setInt( 3, card.getPosition( ) );
        }
        else
        {
            daoUtil.setIntNull( 3 );
        }

        daoUtil.setLong( 4, card.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public boolean isCardWithParentId( String localId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_CARD_ID_BY_PARENT_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, localId );
        daoUtil.executeQuery( );

        boolean result = false;

        if ( daoUtil.next( ) )
        {
            result = true;
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public List<LocalCardDTO> findLocalCardsByParentFolder( String parentId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_CARDS_BY_PARENT_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, parentId );
        daoUtil.executeQuery( );

        List<LocalCardDTO> resultList = new ArrayList<LocalCardDTO>( );

        while ( daoUtil.next( ) )
        {
            LocalCardDTO localCard = new LocalCardDTO( );
            LocalDTO local = new LocalDTO( );
            local.setId( daoUtil.getLong( 1 ) );
            local.setTitle( daoUtil.getString( 2 ) );
            localCard.setLocalDTO( local );
            localCard.setSiblingCardId( daoUtil.getString( 3 ) );
            localCard.setPosition( daoUtil.getInt( 4 ) );
            resultList.add( localCard );
        }

        daoUtil.free( );

        return resultList;
    }
}
