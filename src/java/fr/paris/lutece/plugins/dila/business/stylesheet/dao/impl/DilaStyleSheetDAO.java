/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.dila.business.stylesheet.dao.impl;

import fr.paris.lutece.plugins.dila.business.stylesheet.dao.IDilaStyleSheetDAO;
import fr.paris.lutece.plugins.dila.business.stylesheet.dto.ContentType;
import fr.paris.lutece.plugins.dila.business.stylesheet.dto.DilaStyleSheet;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.util.sql.DAOUtil;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class DilaStyleSheetDAO. Gives access to "DilaStyleSheet" data.
 */
public class DilaStyleSheetDAO implements IDilaStyleSheetDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 7670029586036059570L;

    /** The Constant SQL_QUERY_NEW_PK. */
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_stylesheet) FROM dila_stylesheet";
    private static final String SQL_QUERY_SELECT_ALL_STYLE_SHEET = " SELECT a.id_stylesheet , a.description , a.file_name , a.fk_id_type_contenu , b.label, a.source FROM  dila_stylesheet a LEFT OUTER JOIN dila_type_contenu b ON a.fk_id_type_contenu = b.id_type_contenu ";
    private static final String SQL_QUERY_COUNT_STYLESHEET = " SELECT count(*) FROM dila_stylesheet WHERE fk_id_type_contenu = ? ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_stylesheet ( id_stylesheet , description , file_name, source, fk_id_type_contenu ) " +
        " VALUES ( ?, ? ,?, ?, ? )";
    private static final String SQL_QUERY_SELECT = " SELECT a.id_stylesheet , a.description , a.file_name , a.fk_id_type_contenu , b.label FROM  dila_stylesheet a LEFT OUTER JOIN dila_type_contenu b ON a.fk_id_type_contenu = b.id_type_contenu WHERE a.id_stylesheet = ? ";
    private static final String SQL_QUERY_SELECT_SOURCE = " SELECT source FROM dila_stylesheet WHERE id_stylesheet = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_stylesheet SET id_stylesheet = ?, description = ?, file_name = ?, source = ?, fk_id_type_contenu = ? WHERE id_stylesheet = ?  ";
    private static final String SQL_QUERY_UPDATE_WITHOUT_FILE = " UPDATE dila_stylesheet SET id_stylesheet = ?, description = ?, fk_id_type_contenu = ? WHERE id_stylesheet = ?  ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM dila_stylesheet WHERE id_stylesheet = ? ";
    private static final String SQL_AND = " AND ";
    private static final String SQL_WHERE = " WHERE ";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * Generates a new primary key
     * @return The new primary key
     */
    int newPrimaryKey(  )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;

        daoUtil.free(  );

        return nKey;
    }

    @Override
    public List<DilaStyleSheet> getDilaStyleSheetList( Integer nContentTypeId, String strStyleSheetName )
    {
        List<DilaStyleSheet> dilaStylesheetList = new ArrayList<DilaStyleSheet>(  );
        String strQuery = SQL_QUERY_SELECT_ALL_STYLE_SHEET;
        boolean allreadyWhere = false;

        if ( ( nContentTypeId != null ) && ( nContentTypeId != 0 ) )
        {
            strQuery += " WHERE a.fk_id_type_contenu = ? ";
            allreadyWhere = true;
        }

        boolean isNameNotEmpty = StringUtils.isNotEmpty( strStyleSheetName );

        if ( isNameNotEmpty )
        {
            if ( allreadyWhere )
            {
                strQuery += SQL_AND;
            }
            else
            {
                strQuery += SQL_WHERE;
            }

            strQuery += " a.description LIKE ? ";
        }

        String strOrder = " ORDER BY a.description ";

        String strSQL = strQuery + strOrder;

        DAOUtil daoUtil = new DAOUtil( strSQL, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        Integer nIndex = 1;

        if ( ( nContentTypeId != null ) && ( nContentTypeId != 0 ) )
        {
            daoUtil.setInt( nIndex, nContentTypeId );
            nIndex = 2;
        }

        if ( isNameNotEmpty )
        {
            daoUtil.setString( nIndex, '%' + strStyleSheetName + '%' );
        }

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            DilaStyleSheet dilaStylesheet = new DilaStyleSheet(  );
            dilaStylesheet.setId( daoUtil.getInt( 1 ) );
            dilaStylesheet.setDescription( daoUtil.getString( 2 ) );
            dilaStylesheet.setFile( daoUtil.getString( 3 ) );

            ContentType contentType = new ContentType(  );
            contentType.setId( daoUtil.getInt( 4 ) );
            contentType.setLabel( daoUtil.getString( 5 ) );
            dilaStylesheet.setContentType( contentType );
            dilaStylesheetList.add( dilaStylesheet );
            dilaStylesheet.setSource( daoUtil.getBytes( 6 ) );
        }

        daoUtil.free(  );

        return dilaStylesheetList;
    }

    @Override
    public Integer getStyleSheetNbPerContentType( int nContentTypeId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_COUNT_STYLESHEET, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setInt( 1, nContentTypeId );

        daoUtil.executeQuery(  );

        if ( !daoUtil.next(  ) )
        {
            daoUtil.free(  );
            throw new AppException( DAOUtil.MSG_EXCEPTION_SELECT_ERROR + nContentTypeId );
        }

        int nCount = ( daoUtil.getInt( 1 ) );

        daoUtil.free(  );

        return nCount;
    }

    @Override
    public void create( DilaStyleSheet stylesheet )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        stylesheet.setId( newPrimaryKey(  ) );

        daoUtil.setInt( 1, stylesheet.getId(  ) );
        daoUtil.setString( 2, stylesheet.getDescription(  ) );
        daoUtil.setString( 3, stylesheet.getFile(  ) );
        daoUtil.setBytes( 4, stylesheet.getSource(  ) );
        daoUtil.setInt( 5, stylesheet.getContentType(  ).getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public DilaStyleSheet findByPrimaryKey( Integer nIdStyleSheet )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setInt( 1, nIdStyleSheet );
        daoUtil.executeQuery(  );

        DilaStyleSheet dilaStylesheet = new DilaStyleSheet(  );

        if ( daoUtil.next(  ) )
        {
            dilaStylesheet.setId( daoUtil.getInt( 1 ) );
            dilaStylesheet.setDescription( daoUtil.getString( 2 ) );
            dilaStylesheet.setFile( daoUtil.getString( 3 ) );

            ContentType typeContenu = new ContentType(  );
            typeContenu.setId( daoUtil.getInt( 4 ) );
            typeContenu.setLabel( daoUtil.getString( 5 ) );
            dilaStylesheet.setContentType( typeContenu );
        }

        daoUtil.free(  );

        return dilaStylesheet;
    }

    @Override
    public void update( DilaStyleSheet stylesheet )
    {
        DAOUtil daoUtil;

        if ( stylesheet.getSource(  ) != null )
        {
            daoUtil = new DAOUtil( SQL_QUERY_UPDATE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        }
        else
        {
            daoUtil = new DAOUtil( SQL_QUERY_UPDATE_WITHOUT_FILE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        }

        Integer nIndex = 1;
        daoUtil.setInt( nIndex++, stylesheet.getId(  ) );
        daoUtil.setString( nIndex++, stylesheet.getDescription(  ) );

        if ( stylesheet.getSource(  ) != null )
        {
            daoUtil.setString( nIndex++, stylesheet.getFile(  ) );
            daoUtil.setBytes( nIndex++, stylesheet.getSource(  ) );
        }

        daoUtil.setInt( nIndex++, stylesheet.getContentType(  ).getId(  ) );
        daoUtil.setInt( nIndex++, stylesheet.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public void doDeleteStyleSheet( Integer nIdStyleSheet )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setInt( 1, nIdStyleSheet );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public byte[] getSourceByStyleSheetId( Integer nIdStyleSheet )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_SOURCE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setInt( 1, nIdStyleSheet );
        daoUtil.executeQuery(  );

        byte[] source = null;

        if ( daoUtil.next(  ) )
        {
            source = daoUtil.getBytes( 1 );
        }

        daoUtil.free(  );

        return source;
    }
}
