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
package fr.paris.lutece.plugins.dila.utils;

import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;


/**
 * Dila Utils class
 */
public final class DilaUtils
{
    private static final IDilaLocalService _dilaLocalService = SpringContextService.getBean( "dilaLocalService" );
    private static final IDilaXmlService _dilaXmlService = SpringContextService.getBean( "dilaXmlService" );

    /**
     * Private constructor
     */
    private DilaUtils(  )
    {
    }

    /**
     * Convert the breadcrumb into a displayable format
     * @param originalBreadcrumb original breadcrumb
     * @param idAudience the id audience
     * @return the formatted breadcrumb
     */
    public static String convertBreadcrumbIntoDisplay( String originalBreadcrumb, Long idAudience )
    {
        StringBuilder displayBreadcrumb = new StringBuilder( "" );

        String[] breadcrumbs = originalBreadcrumb.split( ";" );

        for ( String breadcrumb : breadcrumbs )
        {
            if ( StringUtils.isNotBlank( breadcrumb ) )
            {
                if ( StringUtils.isNumeric( breadcrumb ) )
                {
                    displayBreadcrumb.append( _dilaLocalService.findTitleByIdAndTypeAndAudience( Long.valueOf( 
                                breadcrumb ), DilaLocalTypeEnum.FOLDER.getId(  ), idAudience ) );
                }
                else
                {
                    displayBreadcrumb.append( _dilaXmlService.findTitleById( breadcrumb ) );
                }

                displayBreadcrumb.append( " > " );
            }
        }

        return displayBreadcrumb.substring( 0, displayBreadcrumb.length(  ) - 2 );
    }
}
