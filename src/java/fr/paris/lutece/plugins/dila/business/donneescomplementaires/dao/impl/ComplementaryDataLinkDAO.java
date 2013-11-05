package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.impl;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.IComplementaryDataLinkDAO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLearnMoreDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataTeleserviceDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
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

    private static final String TELESERVICE_TABLE = "dila_donnees_complementaires_teleservice";
    private static final String LINK_TABLE = "dila_donnees_complementaires_savoir_plus";

    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM <%TABLE%>";
    private static final String SQL_QUERY_SELECT_BY_ID = "SELECT id, titre, url, position"
            + " FROM <%TABLE%> WHERE fk_donnees_complementaires_id = ? ORDER BY position ASC";
    private static final String SQL_QUERY_INSERT = " INSERT INTO <%TABLE%> "
            + "( id, titre, url, position, fk_donnees_complementaires_id ) VALUES (?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_DELETE_BY_ID = " DELETE FROM <%TABLE%> "
            + "WHERE fk_donnees_complementaires_id = ?";

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
    public List<ComplementaryDataLinkDTO> findByDataId( Long idDonneeComplementaire,
            ComplementaryLinkTypeEnum type )
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
