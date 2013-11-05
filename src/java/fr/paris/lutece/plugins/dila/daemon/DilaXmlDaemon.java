package fr.paris.lutece.plugins.dila.daemon;

import fr.paris.lutece.plugins.dila.exception.DilaException;
import fr.paris.lutece.plugins.dila.service.IDilaDownloadService;
import fr.paris.lutece.plugins.dila.service.IDilaExtractService;
import fr.paris.lutece.plugins.dila.service.IDilaIndexationService;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.spring.SpringContextService;


/**
 * Daemon for Dila XMl indexation and download
 */
public class DilaXmlDaemon extends Daemon
{
    private IDilaDownloadService _dilaDownloadService;
    private IDilaExtractService _dilaExtractService;
    private IDilaIndexationService _dilaIndexationService;

    @Override
    public void run( )
    {
        StringBuilder sb = new StringBuilder( );
        setServices( );

        try
        {
            _dilaDownloadService.downloadAll( );
            sb.append( "1 - Download of archives done." );
            sb.append( "\n" );
            _dilaExtractService.extractAll( );
            sb.append( "2 - Extraction of archives done." );
            sb.append( "\n" );
            _dilaIndexationService.indexAll( );
            sb.append( "3 - Indexing of archives done." );
            sb.append( "\n" );
        }
        catch ( DilaException e )
        {
            sb.append( e.getMessage( ) );
            sb.append( "\n" );
        }
        setLastRunLogs( sb.toString( ) );
    }

    /**
     * Set daemon services
     */
    private void setServices( )
    {
        if ( _dilaDownloadService == null )
        {
            _dilaDownloadService = SpringContextService.getBean( "dilaDownloadService" );
        }

        if ( _dilaExtractService == null )
        {
            _dilaExtractService = SpringContextService.getBean( "dilaExtractService" );
        }

        if ( _dilaIndexationService == null )
        {
            _dilaIndexationService = SpringContextService.getBean( "dilaIndexationService" );
        }
    }
}
