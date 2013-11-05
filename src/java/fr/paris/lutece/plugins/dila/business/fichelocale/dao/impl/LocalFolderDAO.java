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
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id_dossier_local) FROM dila_dossier_local";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_dossier_local "
            + "( id_dossier_local, id_theme_parent, id_dossier_frere, position, presentation, fk_id_local ) "
            + " VALUES ( ?, ? ,?, ?, ?, ? )";
    private static final String SQL_QUERY_SELECT_FOLDER_ID_BY_LOCAL_ID = "SELECT "
            + "id_dossier_local FROM dila_dossier_local WHERE fk_id_local = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM dila_dossier_local WHERE id_dossier_local = ?";
    private static final String SQL_QUERY_SELECT_FOLDER_BY_LOCAL_ID = "SELECT "
            + "dossier.id_dossier_local, dossier.id_theme_parent, dossier.id_dossier_frere, dossier.position, dossier.presentation, local.titre, local.auteur, local.fk_audience_id "
            + "FROM dila_dossier_local dossier, dila_local local "
            + "WHERE dossier.fk_id_local = ? AND dossier.fk_id_local = local.id_local";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_dossier_local"
            + " SET id_theme_parent = ?, id_dossier_frere = ?, position = ?, presentation = ?"
            + " WHERE id_dossier_local = ?";
    private static final String SQL_QUERY_FIND_FOLDERS_BY_PARENT_ID = "SELECT l.id_local, l.titre, d.id_dossier_frere, d.position"
            + " FROM dila_dossier_local d JOIN dila_local l ON d.fk_id_local = l.id_local"
            + " WHERE d.id_theme_parent = ?";

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
            resultList.add( localFolder );
        }

        daoUtil.free( );

        return resultList;
    }

}
