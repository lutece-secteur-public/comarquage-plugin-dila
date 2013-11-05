package fr.paris.lutece.plugins.dila.business.fichelocale.dao.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IAudienceDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link IAudienceDAO}
 */
public class AudienceDAO implements IAudienceDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 5329588195127530638L;

    private static final String SQL_QUERY_SELECT_ALL = "SELECT id, label FROM dila_audience ORDER BY label ASC";

    @Override
    public List<AudienceDTO> findAll( )
    {
        List<AudienceDTO> result = new ArrayList<AudienceDTO>( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.executeQuery( );

        // Loop for each result
        while ( daoUtil.next( ) )
        {
            AudienceDTO audience = new AudienceDTO( );
            audience.setId( daoUtil.getLong( 1 ) );
            audience.setLabel( daoUtil.getString( 2 ) );

            result.add( audience );
        }

        daoUtil.free( );

        return result;
    }

}
