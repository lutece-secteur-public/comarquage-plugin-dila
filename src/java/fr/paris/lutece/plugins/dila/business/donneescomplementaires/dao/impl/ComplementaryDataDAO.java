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

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.IComplementaryDataDAO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link IComplementaryDataDAO}
 */
public class ComplementaryDataDAO implements IComplementaryDataDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -4819383738721769830L;
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_complementary_data";
    private static final String SQL_QUERY_SELECT_ALL = "SELECT donnee.id, xml.id_xml, xml.title"
            + " FROM dila_complementary_data donnee, dila_xml xml WHERE donnee.xml_id = xml.id"
            + " ORDER BY donnee.id ASC";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_complementary_data "
            + "( id, bottom_block, column_block, xml_id, audience_id ) VALUES ( ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_FIND_EXISTING_CARD = " SELECT id FROM dila_complementary_data "
            + "WHERE xml_id = ?";
    private static final String SQL_QUERY_FIND_BY_ID = " SELECT donnee.bottom_block, donnee.column_block, xml.id_xml, donnee.audience_id "
            + " FROM dila_complementary_data donnee, dila_xml xml WHERE donnee.id = ? AND donnee.xml_id = xml.id";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_complementary_data "
            + "SET bottom_block = ?, column_block = ? WHERE id = ?";
    private static final String SQL_QUERY_DELETE = " DELETE FROM dila_complementary_data WHERE id = ?";
    private static final String SQL_QUERY_FIND_BY_CARD_AND_AUDIENCE = "SELECT id, bottom_block, column_block "
            + " FROM dila_complementary_data WHERE xml_id = ? AND audience_id = ?";

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
    public List<ComplementaryDataDTO> findAll( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.executeQuery( );

        List<ComplementaryDataDTO> result = new ArrayList<ComplementaryDataDTO>( );

        while ( daoUtil.next( ) )
        {
            ComplementaryDataDTO donnee = new ComplementaryDataDTO( );
            XmlDTO fiche = new XmlDTO( );

            donnee.setId( daoUtil.getLong( 1 ) );
            fiche.setIdXml( daoUtil.getString( 2 ) );
            fiche.setTitle( daoUtil.getString( 3 ) );

            donnee.setCard( fiche );

            result.add( donnee );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public Long insert( ComplementaryDataDTO dto )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        dto.setId( newPrimaryKey( ) );

        daoUtil.setLong( 1, dto.getId( ) );
        daoUtil.setString( 2, dto.getBottomBlock( ) );
        daoUtil.setString( 3, dto.getColumnBlock( ) );
        daoUtil.setLong( 4, dto.getCard( ).getId( ) );
        daoUtil.setLong( 5, dto.getIdAudience( ) );

        daoUtil.executeUpdate( );

        daoUtil.free( );

        return dto.getId( );
    }

    @Override
    public boolean cardHasComplement( String id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_EXISTING_CARD, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setString( 1, id );

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
    public ComplementaryDataDTO findById( Long id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, id );

        ComplementaryDataDTO result = null;

        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            result = new ComplementaryDataDTO( );

            result.setId( id );
            result.setBottomBlock( daoUtil.getString( 1 ) );
            result.setColumnBlock( daoUtil.getString( 2 ) );
            result.getCard( ).setIdXml( daoUtil.getString( 3 ) );
            result.setIdAudience( daoUtil.getLong( 4 ) );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public void store( ComplementaryDataDTO donneesComplementaires )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setString( 1, donneesComplementaires.getBottomBlock( ) );
        daoUtil.setString( 2, donneesComplementaires.getColumnBlock( ) );
        daoUtil.setLong( 3, donneesComplementaires.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void delete( Long id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setLong( 1, id );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public ComplementaryDataDTO findByCardAndAudience( Long ficheId, Long audienceId )
    {
        ComplementaryDataDTO donneeComplementaireDTO = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_CARD_AND_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, ficheId );
        daoUtil.setLong( 2, audienceId );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            donneeComplementaireDTO = new ComplementaryDataDTO( );
            donneeComplementaireDTO.setId( daoUtil.getLong( 1 ) );
            donneeComplementaireDTO.setBottomBlock( daoUtil.getString( 2 ) );
            donneeComplementaireDTO.setColumnBlock( daoUtil.getString( 3 ) );
        }

        daoUtil.free( );

        return donneeComplementaireDTO;
    }
}
