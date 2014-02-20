/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.dila.daemon;

import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = Logger.getLogger( DilaXmlDaemon.class );
    private IDilaDownloadService _dilaDownloadService;
    private IDilaExtractService _dilaExtractService;
    private IDilaIndexationService _dilaIndexationService;

    @Override
    public void run(  )
    {
        StringBuilder sb = new StringBuilder(  );
        setServices(  );

        try
        {
            _dilaDownloadService.downloadAll(  );
            sb.append( "1 - Download of archives done." );
            sb.append( "\n" );
            _dilaExtractService.extractAll(  );
            sb.append( "2 - Extraction of archives done." );
            sb.append( "\n" );
            _dilaIndexationService.indexAll(  );
            sb.append( "3 - Indexing of archives done." );
            sb.append( "\n" );
        }
        catch ( DilaException e )
        {
            LOGGER.error( "Error during Dila Daemon execution", e );
            sb.append( e.getMessage(  ) );
            sb.append( "\n" );
        }

        setLastRunLogs( sb.toString(  ) );
    }

    /**
     * Set daemon services
     */
    private void setServices(  )
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
