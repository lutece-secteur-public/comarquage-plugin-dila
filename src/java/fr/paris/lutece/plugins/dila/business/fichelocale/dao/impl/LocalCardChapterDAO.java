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

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalCardChapterDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of IChapitreFicheLocaleDAO
 */
public class LocalCardChapterDAO implements ILocalCardChapterDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 1871080044687399057L;
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_local_card_chapter";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_local_card_chapter "
            + "( id, title, content, position, local_card_id ) VALUES ( ?, ? ,?, ?, ?)";
    private static final String SQL_QUERY_DELETE_BY_CARD_ID = " DELETE FROM dila_local_card_chapter "
            + "WHERE local_card_id = ?";
    private static final String SQL_QUERY_FIND_BY_CARD_ID = " SELECT id, title, content, position from dila_local_card_chapter WHERE local_card_id = ? ORDER BY id ASC";

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
            nKey = daoUtil.getLong( 1 ) + 1L;
        }

        daoUtil.free( );

        return nKey;
    }

    @Override
    public void create( LocalCardChapterDTO chapter )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        chapter.setLocalCardChapterId( newPrimaryKey( ) );

        daoUtil.setLong( 1, chapter.getLocalCardChapterId( ) );
        daoUtil.setString( 2, chapter.getTitle( ) );
        daoUtil.setString( 3, chapter.getContent( ) );
        daoUtil.setInt( 4, chapter.getPosition( ) );
        daoUtil.setLong( 5, chapter.getLocalCard( ).getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void deleteByCardId( Long ficheId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_CARD_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setLong( 1, ficheId );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public List<LocalCardChapterDTO> findByCardId( Long cardId )
    {
        List<LocalCardChapterDTO> results = new ArrayList<LocalCardChapterDTO>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_CARD_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setLong( 1, cardId );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            LocalCardChapterDTO chapter = new LocalCardChapterDTO( );

            chapter.setLocalCardChapterId( daoUtil.getLong( 1 ) );
            chapter.setTitle( daoUtil.getString( 2 ) );
            chapter.setContent( daoUtil.getString( 3 ) );
            chapter.setPosition( daoUtil.getInt( 4 ) );

            results.add( chapter );
        }

        daoUtil.free( );

        return results;
    }
}
