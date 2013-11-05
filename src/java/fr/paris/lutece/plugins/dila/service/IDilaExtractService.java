package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.exception.DilaException;

import java.io.File;


/**
 * Interface for archive extract
 */
public interface IDilaExtractService
{
    /**
     * Extract all files
     * @throws DilaException Daemon exception
     */
    void extractAll( ) throws DilaException;

    /**
     * Check if parameter are valid
     * @param strArchivesDirPath the dir to check
     * @return <code>true</code> if parameters are valid; else
     *         <code>false</code>
     */
    boolean isValidParameters( String strArchivesDirPath );

    /**
     * Extract file
     * @param file file to extract
     * @param strArchiveName the archive name
     * @param strDirPathExtract the dir path
     * @param strDirPathCopy the dir copy path
     * @throws Exception occurs during treatment
     */
    void extract( File file, String strArchiveName, String strDirPathExtract, String strDirPathCopy ) throws Exception;
}
