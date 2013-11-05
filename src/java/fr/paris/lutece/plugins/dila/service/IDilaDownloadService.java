package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.exception.DilaException;


/**
 * Interface for download daemon
 */
public interface IDilaDownloadService
{
    /**
     * Download all files
     * @throws DilaException daemon error
     */
    void downloadAll( ) throws DilaException;
}
