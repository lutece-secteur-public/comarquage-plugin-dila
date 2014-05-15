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
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.exception.DilaException;
import fr.paris.lutece.plugins.dila.service.IDilaExtractService;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.plugins.dila.utils.filter.FilenameFilterArchive;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * Implementation of {@link IDilaExtractService}
 */
public class DilaExtractService implements IDilaExtractService
{
    private static final Logger LOGGER = Logger.getLogger( DilaExtractService.class );
    /**
     * Directory path to archives
     */
    private String _strArchivesDirPath;

    /**
     * Archives filter
     */
    private FilenameFilterArchive _filenameFilterArchive = new FilenameFilterArchive( );

    @Override
    public void extractAll( ) throws DilaException
    {
        AppLogService.info( "Begin extract" );

        _strArchivesDirPath = AppPropertiesService.getProperty( DilaConstants.PROPERTY_TMP_DIRECTORY );

        if ( !isValidParameters( _strArchivesDirPath ) )
        {
            AppLogService.error( "Could not extract archives : invalid parameter(s)." );
            throw new DilaException( "Could not extract archives : invalid parameter(s)." );
        }
        else
        {
            File dirZip = new File( _strArchivesDirPath );

            File[] listFileZip = dirZip.listFiles( _filenameFilterArchive );

            if ( listFileZip.length != 4 )
            {
                AppLogService.debug( "Archives directory doesn't contain all archives" );
            }

            // List archives
            for ( File file : listFileZip )
            {
                AppLogService.debug( "Processing archive : " + file.getName( ) );

                // Define extract directory
                String strArchiveName = file.getName( );
                String strDirPathExtract = null;
                String strDirPathCopy = null;

                if ( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_INDIVIDUALS ).equals(
                        strArchiveName ) )
                {
                    AppLogService.debug( "Archive type : individual" );
                    strDirPathExtract = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_TMP_INDIVIDUALS );
                    strDirPathCopy = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_INDIVIDUALS );
                }
                else if ( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_ASSO ).equals(
                        strArchiveName ) )
                {
                    AppLogService.debug( "Archive type : association" );
                    strDirPathExtract = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_TMP_ASSO );
                    strDirPathCopy = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_ASSO );
                }
                else if ( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_PME ).equals(
                        strArchiveName ) )
                {
                    AppLogService.debug( "Archive type : professional" );
                    strDirPathExtract = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_TMP_PME );
                    strDirPathCopy = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_PME );
                }
                else if ( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_LOCALES ).equals(
                        strArchiveName ) )
                {
                    AppLogService.debug( "Archive type : local data" );
                    strDirPathExtract = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_TMP_LOCALES );
                    strDirPathCopy = AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_LOCALES );
                }
                else
                {
                    AppLogService.error( "Could not define extract directory path" );
                    throw new DilaException( "Could not define extract directory path" );
                }

                try
                {
                    extract( file, strArchiveName, strDirPathExtract, strDirPathCopy );
                }
                catch ( IOException e )
                {
                    throw new DilaException( e.getMessage( ) );
                }
                catch ( Exception e )
                {
                    throw new DilaException( e.getMessage( ) );
                }
            }
        }

        AppLogService.info( "End extract" );
    }

    @Override
    public void extract( File file, String strArchiveName, String strDirPathExtract, String strDirPathCopy )
            throws Exception
    {
        if ( StringUtils.isNotBlank( strDirPathExtract ) )
        {
            File dirExtract = new File( strDirPathExtract );

            if ( ( dirExtract.exists( ) ) )
            {
                FileUtils.forceDelete( dirExtract );
            }

            FileUtils.forceMkdir( dirExtract );
        }

        if ( StringUtils.isNotBlank( strDirPathCopy ) )
        {
            File dirCopy = new File( strDirPathCopy );
            FileUtils.deleteDirectory( dirCopy );
        }

        if ( strArchiveName.endsWith( DilaConstants.EXTENSION_ZIP ) )
        {
            // Extract archive
            extractArchive( file.getAbsolutePath( ), strDirPathExtract );
            copyFiles( strDirPathExtract, strDirPathCopy, false );
            deleteArchiveFile( file );
        }
        else if ( strArchiveName.endsWith( DilaConstants.EXTENSION_BZIP2 ) )
        {
            BZip2CompressorInputStream bzis = null;
            BufferedOutputStream bos = null;

            try
            {
                // Uncompress
                BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
                String strUncompressedFilename = BZip2Utils.getUncompressedFilename( file.getName( ) );
                String strOutputPath = strDirPathExtract + File.separator + strUncompressedFilename;
                bzis = new BZip2CompressorInputStream( bis );

                FileOutputStream fos = new FileOutputStream( strOutputPath );
                bos = new BufferedOutputStream( fos );
                IOUtils.copy( bzis, bos );

                bzis.close( );
                bos.close( );
                // Extract archive
                extractArchive( strOutputPath, strDirPathExtract );
                copyFiles( strDirPathExtract, strDirPathCopy, true );
                deleteArchiveFile( file );
            }
            catch ( Exception e )
            {
                AppLogService.debug( e.getMessage( ) );
                throw new DilaException( "Error during extract of " + file.getName( ) );
            }
            finally
            {
                IOUtils.closeQuietly( bzis );
                IOUtils.closeQuietly( bos );
            }
        }
        else
        {
            throw new DilaException( "Could not define extract directory path." );
        }
    }

    @Override
    public boolean isValidParameters( String strArchivesDirPath )
    {
        boolean bValid = true;

        if ( StringUtils.isBlank( strArchivesDirPath ) )
        {
            bValid = false;
            LOGGER.error( "Invalid parameter : blank archive dir path " + strArchivesDirPath );
        }
        else
        {
            File dirZip = new File( strArchivesDirPath );

            if ( !dirZip.isDirectory( ) )
            {
                bValid = false;
                LOGGER.error( "Invalid parameter : archive dir "+ strArchivesDirPath +" is not a directory" );
            }
            else
            {
                File[] listFileZip = dirZip.listFiles( _filenameFilterArchive );

                if ( ( listFileZip == null ) || ( listFileZip.length < 1 ) )
                {
                    bValid = false;
                    LOGGER.error( "Invalid parameter : archive dir "+ strArchivesDirPath +" is empty" );
                }
            }
        }

        return bValid;
    }

    /**
     * Extract an archive
     * @param strArchive the archive
     * @param strDirPathExtract the extract path dir
     * @throws Exception on batch error
     */
    private void extractArchive( String strArchive, String strDirPathExtract ) throws Exception
    {
        BufferedOutputStream out = null;

        try
        {
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( strArchive ) );
            ArchiveInputStream in = new ArchiveStreamFactory( ).createArchiveInputStream( bis );
            ArchiveEntry entry = in.getNextEntry( );

            while ( entry != null )
            {
                File entryFile = new File( strDirPathExtract, entry.getName( ) );

                if ( entry.isDirectory( ) && !entryFile.exists( ) )
                {
                    FileUtils.forceMkdir( entryFile );
                }
                else
                {
                    out = new BufferedOutputStream( new FileOutputStream( entryFile ) );
                    IOUtils.copy( in, out );
                    out.close( );
                }

                entry = in.getNextEntry( );
            }

            in.close( );
        }
        catch ( IOException e )
        {
            throw new DilaException( e.getMessage( ) );
        }
        catch ( ArchiveException e )
        {
            throw new DilaException( e.getMessage( ) );
        }
        finally
        {
            IOUtils.closeQuietly( out );
        }
    }

    /**
     * Method to copy files
     * @param srcDir the src dir
     * @param destDir the dest dir
     * @param copySubFolders if need to copy all subfolders
     * @throws IOException occurs during treatment
     */
    private void copyFiles( String srcDir, String destDir, boolean copySubFolders ) throws IOException
    {
        File source = new File( srcDir );
        File dest = new File( destDir );

        if ( copySubFolders )
        {
            for ( File f : source.listFiles( ) )
            {
                if ( f.isDirectory( ) )
                {
                    FileUtils.moveDirectory( f, dest );
                }
            }

            // Clean locales folder
            FileUtils.forceDelete( source );
        }
        else
        {
            FileUtils.moveDirectory( source, dest );
        }
    }

    /**
     * Delete archive file
     * @param file file to delete
     * @throws IOException occurs during treatment
     */
    private void deleteArchiveFile( File file ) throws IOException
    {
        if ( !file.delete( ) )
        {
            throw new IOException( "Erreur lors de la suppression du fichier." );
        }
    }
}
