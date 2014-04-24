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
package fr.paris.lutece.plugins.dila.web;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.plugins.dila.service.IDilaCacheService;
import fr.paris.lutece.plugins.dila.utils.CacheKeyUtils;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;


/**
 * Dila rendering page
 */
public class DilaApp implements XPageApplication
{
    // Templates
    private static final String TEMPLATE_XPAGE_DILA = "skin/plugins/dila/page_dila.html";

    // Markers
    private static final String MARKER_CATEGORY = "categorie";
    private static final String MARKER_INSEE = "insee";
    private static final String MARKER_DATA = "dila_data";
    private static final String MARK_NO_DATA = "noData";
    private static final String MARK_URL_INDIVIDUALS = "urlParticuliers";
    private static final String MARK_URL_PROFESSIONALS = "urlPME";
    private static final String MARK_URL_ASSOCIATIONS = "urlAssociations";
    private static final String PARAMETER_XML_FILE = "xmlFile";
    private IDilaCacheService _dilaCacheService = SpringContextService.getBean( "dilaCacheService" );

    /**
     * {@inheritDoc}
     */
    @Override
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin ) throws UserNotSignedException,
            SiteMessageException
    {
        HashMap<String, Object> model = new HashMap<String, Object>( );
        String xmlName = request.getParameter( PARAMETER_XML_FILE );
        String strCategory = request.getParameter( MARKER_CATEGORY );
        strCategory = WordUtils.capitalize( strCategory );

        String dilaData = "";

        if ( StringUtils.isNotBlank( strCategory ) && ( AudienceCategoryEnum.fromLabel( strCategory ) != null ) )
        {
            AudienceCategoryEnum enumCategory = AudienceCategoryEnum.fromLabel( strCategory );

            if ( StringUtils.isBlank( xmlName ) )
            {
                String prefix = null;
                switch ( enumCategory )
                {
                case ASSOCIATIONS:
                    prefix = DilaConstants.ASSOCIATION_PREFIX;
                    break;
                case PROFESSIONNALS:
                    prefix = DilaConstants.PROFESSIONAL_PREFIX;
                    break;
                case INDIVIDUALS:
                default:
                    prefix = DilaConstants.INDIVIDUAL_PREFIX;
                    break;
                }
                xmlName = AppPropertiesService.getProperty( prefix + DilaConstants.PROPERTY_HOME_CARD );
            }

            String cacheKey = CacheKeyUtils.generateCacheKey( enumCategory.getId( ), xmlName );
            dilaData = _dilaCacheService.getRessource( cacheKey, request.getLocale( ) );
            model.put( MARK_NO_DATA, false );
        }
        else
        {
            model.put( MARK_NO_DATA, true );
        }

        model.put( MARK_URL_ASSOCIATIONS, DilaConstants.XPAGE_ASSO );
        model.put( MARK_URL_INDIVIDUALS, DilaConstants.XPAGE_PARTICULIERS );
        model.put( MARK_URL_PROFESSIONALS, DilaConstants.XPAGE_PME );
        model.put( MARKER_CATEGORY, strCategory );
        model.put( MARKER_DATA, dilaData );
        model.put( MARKER_INSEE, AppPropertiesService.getProperty( DilaConstants.PROPERTY_INSEE ) );

        XPage page = new XPage( );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_XPAGE_DILA, request.getLocale( ), model );
        page.setContent( template.getHtml( ) );
        page.setTitle( DilaPlugin.PLUGIN_NAME );
        page.setPathLabel( DilaPlugin.PLUGIN_NAME );

        return page;
    }
}
