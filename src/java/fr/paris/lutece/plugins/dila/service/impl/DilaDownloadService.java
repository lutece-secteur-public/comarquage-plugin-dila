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
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.exception.DilaException;
import fr.paris.lutece.plugins.dila.service.IDilaDownloadService;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.plugins.dila.utils.http.HttpRequestInterceptorBzip2;
import fr.paris.lutece.plugins.dila.utils.http.HttpResponseInterceptorBzip2;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;


/**
 * Implementation of {@link IDilaDownloadService}
 */
public class DilaDownloadService implements IDilaDownloadService
{
    private static final String HTTP_ACCESS_PROXY_PASSWORD = "httpAccess.proxyPassword";
    private static final String HTTP_ACCESS_PROXY_USERNAME = "httpAccess.proxyUserName";
    private static final String HTTP_ACCESS_PROXY_PORT = "httpAccess.proxyPort";
    private static final String HTTP_ACCESS_PROXY_HOST = "httpAccess.proxyHost";

    @Override
    public void downloadAll(  ) throws DilaException
    {
        // individuals
        download( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_URL_INDIVIDUALS ),
            AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_INDIVIDUALS ) );
        // associations
        download( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_URL_ASSO ),
            AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_ASSO ) );
        // professionnals
        download( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_URL_PME ),
            AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_PME ) );
        // local
        download( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_URL_LOCALES ),
            AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_NAME_LOCALES ) );
    }

    /**
     * Download one file
     * @param strArchiveUrl the url
     * @param strArchiveName the name
     * @throws DilaException daemon error
     */
    private void download( String strArchiveUrl, String strArchiveName )
        throws DilaException
    {
        CloseableHttpClient client = null;
        HttpClientBuilder clientBuilder = HttpClients.custom(  );
        HttpGet request = new HttpGet( strArchiveUrl + strArchiveName );
        String proxyHost = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_HOST );

        // Connection through proxy
        if ( StringUtils.isNotBlank( proxyHost ) )
        {
            String proxyPort = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_PORT );
            String proxyUser = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_USERNAME );
            String proxyPassword = AppPropertiesService.getProperty( HTTP_ACCESS_PROXY_PASSWORD );

            //Proxy authentication
            if ( StringUtils.isNotBlank( proxyUser ) )
            {
                CredentialsProvider credsProvider = new BasicCredentialsProvider(  );
                credsProvider.setCredentials( new AuthScope( proxyHost, Integer.parseInt( proxyPort ) ),
                    new UsernamePasswordCredentials( proxyUser, proxyPassword ) );
                clientBuilder.setDefaultCredentialsProvider( credsProvider );
            }

            String strSchema = request.getURI(  ).getScheme(  );
            HttpHost proxy = new HttpHost( proxyHost, Integer.parseInt( proxyPort ), strSchema );
            RequestConfig config = RequestConfig.custom(  ).setProxy( proxy ).build(  );
            request.setConfig( config );
        }

        // Special header to download bzip2
        if ( strArchiveName.endsWith( ".bz2" ) )
        {
            clientBuilder.addInterceptorFirst( new HttpRequestInterceptorBzip2(  ) )
                         .addInterceptorFirst( new HttpResponseInterceptorBzip2(  ) );
        }

        // Build client
        client = clientBuilder.build(  );

        CloseableHttpResponse response = null;

        try
        {
            response = client.execute( request );
        }
        catch ( ClientProtocolException e )
        {
            AppLogService.error( "Error on HTTP protocol" );
            throw new DilaException( "Error on HTTP protocol" );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error on HTTP protocol" );
            throw new DilaException( "Error on HTTP protocol" );
        }

        // Get response status
        int nResponseStatusCode = response.getStatusLine(  ).getStatusCode(  );
        AppLogService.debug( "Response status : " + nResponseStatusCode );

        if ( nResponseStatusCode != HttpURLConnection.HTTP_OK )
        {
            AppLogService.debug( "Error URL connection status != " + HttpURLConnection.HTTP_OK );
        }

        // Buffer response
        BufferedInputStream bis = null;

        try
        {
            bis = new BufferedInputStream( response.getEntity(  ).getContent(  ) );
        }
        catch ( Exception e )
        {
            AppLogService.error( "Error on get reponse entity content" );
            throw new DilaException( "Error on get reponse entity content" );
        }

        String strArchivesDirPath = AppPropertiesService.getProperty( DilaConstants.PROPERTY_TMP_DIRECTORY );

        String strFileZipPathName = FilenameUtils.concat( strArchivesDirPath, strArchiveName );
        File fileZip = new File( strFileZipPathName );
        File dirZip = new File( strArchivesDirPath );

        if ( ( dirZip != null ) && !dirZip.exists(  ) )
        {
            AppLogService.debug( "Create directory to save archives : " + strArchivesDirPath );

            try
            {
                FileUtils.forceMkdir( dirZip );
            }
            catch ( IOException e )
            {
                AppLogService.error( "Error on create directory archives" );
                throw new DilaException( "Error on create directory archives" );
            }
        }

        BufferedOutputStream bos = null;

        try
        {
            bos = new BufferedOutputStream( new FileOutputStream( fileZip ) );
        }
        catch ( FileNotFoundException e )
        {
            AppLogService.error( "Error on create archive." );
            throw new DilaException( "Error on create archive." );
        }

        try
        {
            IOUtils.copy( bis, bos );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error on copy input to output stream" );
            throw new DilaException( "Error on copy input to output stream" );
        }

        // Close response
        try
        {
            response.close(  );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error on close response" );
            throw new DilaException( "Error on close response" );
        }

        // Close output buffer
        try
        {
            bos.close(  );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error on close output stream" );
            throw new DilaException( "Error on close output stream" );
        }

        // Close input buffer
        try
        {
            bis.close(  );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error on close input stream" );
            throw new DilaException( "Error on close input stream" );
        }

        // Close client connection
        try
        {
            client.close(  );
        }
        catch ( IOException e )
        {
            AppLogService.error( "Error on close http client" );
            throw new DilaException( "Error on close http client" );
        }
    }
}
