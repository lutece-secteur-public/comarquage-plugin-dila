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

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.exception.DilaException;
import fr.paris.lutece.plugins.dila.service.IDilaBatchXMLService;
import fr.paris.lutece.plugins.dila.service.IDilaIndexationService;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.plugins.dila.utils.filter.FilenameFilterXml;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Implementation of {@link IDilaIndexationService}
 */
public class DilaIndexationService implements IDilaIndexationService
{
    @Inject
    @Named( "dilaBatchXmlService" )
    private IDilaBatchXMLService _dilaBatchXMLService;

    @Override
    public void indexAll(  ) throws DilaException
    {
        AppLogService.info( "Begin index" );

        // Individual
        process( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_INDIVIDUALS ),
            DilaConstants.PROCESS_INDIVIDUAL, AudienceCategoryEnum.INDIVIDUALS );

        // Association
        process( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_ASSO ),
            DilaConstants.PROCESS_ASSOCIATION, AudienceCategoryEnum.ASSOCIATIONS );

        // Professional
        process( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_PME ),
            DilaConstants.PROCESS_PROFESSIONAL, AudienceCategoryEnum.PROFESSIONNALS );

        _dilaBatchXMLService.delete(  );
    }

    /**
     * Process XML files
     * @param strDirPath directory path to XML files
     * @param strProcessName process name
     * @param typeXml the type XML
     * @throws IOException
     * @throws JDOMException
     * @throws DilaException occurs during treatment
     */
    private void process( String strDirPath, String strProcessName, AudienceCategoryEnum typeXml )
        throws DilaException
    {
        File dirXML = new File( strDirPath );

        if ( dirXML.exists(  ) )
        {
            File[] listXML = dirXML.listFiles( new FilenameFilterXml(  ) );

            if ( listXML != null )
            {
                AppLogService.info( "Processing " + strProcessName + " XML files available in directory " + strDirPath );

                for ( File file : listXML )
                {
                    try
                    {
                        _dilaBatchXMLService.processXMLFile( file, typeXml );
                    }
                    catch ( JDOMException jdom )
                    {
                        throw new DilaException( "Error during file " + file.getName(  ) + " parsing.", jdom );
                    }
                    catch ( IOException ioe )
                    {
                        throw new DilaException( "Error during file " + file.getName(  ) + " management.", ioe );
                    }
                    catch ( Exception e )
                    {
                        throw new DilaException( "Error during file " + file.getName(  ) + " indexation : "+e.getMessage( ), e );
                    }
                }
            }
            else
            {
                AppLogService.info( "No " + strProcessName + " XML files in directory " + strDirPath );
            }
        }
    }
}
