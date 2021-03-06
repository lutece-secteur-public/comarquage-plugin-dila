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
package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.impl;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.IComplementaryDataLinkDAO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLearnMoreDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataTeleserviceDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link IComplementaryDataLinkDAO}
 */
public class ComplementaryDataLinkDAO implements IComplementaryDataLinkDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 3317028742153081496L;
    private static final String TELESERVICE_TABLE = "dila_complementary_data_teleservice";
    private static final String LINK_TABLE = "dila_complementary_data_learn_more";
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM <%TABLE%>";
    private static final String SQL_QUERY_SELECT_BY_ID = "SELECT id, title, url, position"
            + " FROM <%TABLE%> WHERE complementary_data_id = ? ORDER BY position ASC";
    private static final String SQL_QUERY_INSERT = " INSERT INTO <%TABLE%> "
            + "( id, title, url, position, complementary_data_id ) VALUES (?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_DELETE_BY_ID = " DELETE FROM <%TABLE%> " + "WHERE complementary_data_id = ?";

    /**
     * Generate query
     * @param strQuery the query to generate
     * @param type the type of dto
     * @return the formatted query
     */
    private String getQuery( String strQuery, ComplementaryLinkTypeEnum type )
    {
        if ( type.equals( ComplementaryLinkTypeEnum.LEARN_MORE ) )
        {
            return strQuery.replace( "<%TABLE%>", LINK_TABLE );
        }
        else
        {
            return strQuery.replace( "<%TABLE%>", TELESERVICE_TABLE );
        }
    }

    /**
     * Generates a new primary key
     * @param type the type of dto to add
     * @return The new primary key
     */
    private Long newPrimaryKey( ComplementaryLinkTypeEnum type )
    {
        DAOUtil daoUtil = new DAOUtil( getQuery( SQL_QUERY_NEW_PK, type ),
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
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
    public void insert( ComplementaryDataLinkDTO dto )
    {
        DAOUtil daoUtil = new DAOUtil( getQuery( SQL_QUERY_INSERT, dto.getType( ) ),
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        dto.setId( newPrimaryKey( dto.getType( ) ) );

        daoUtil.setLong( 1, dto.getId( ) );
        daoUtil.setString( 2, dto.getTitle( ) );
        daoUtil.setString( 3, dto.getURL( ) );
        daoUtil.setInt( 4, dto.getPosition( ) );
        daoUtil.setLong( 5, dto.getIdComplementaryData( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void delete( Long id, ComplementaryLinkTypeEnum type )
    {
        DAOUtil daoUtil = new DAOUtil( getQuery( SQL_QUERY_DELETE_BY_ID, type ),
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setLong( 1, id );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public List<ComplementaryDataLinkDTO> findByDataId( Long idDonneeComplementaire, ComplementaryLinkTypeEnum type )
    {
        DAOUtil daoUtil = new DAOUtil( getQuery( SQL_QUERY_SELECT_BY_ID, type ),
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setLong( 1, idDonneeComplementaire );

        daoUtil.executeQuery( );

        List<ComplementaryDataLinkDTO> result = new ArrayList<ComplementaryDataLinkDTO>( );

        while ( daoUtil.next( ) )
        {
            ComplementaryDataLinkDTO complement = null;

            if ( type.equals( ComplementaryLinkTypeEnum.TELESERVICE ) )
            {
                complement = new ComplementaryDataTeleserviceDTO( );
            }
            else
            {
                complement = new ComplementaryDataLearnMoreDTO( );
            }

            complement.setId( daoUtil.getLong( 1 ) );
            complement.setTitle( daoUtil.getString( 2 ) );
            complement.setURL( daoUtil.getString( 3 ) );
            complement.setPosition( daoUtil.getInt( 4 ) );
            complement.setIdComplementaryData( idDonneeComplementaire );

            result.add( complement );
        }

        daoUtil.free( );

        return result;
    }
}
