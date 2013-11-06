/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.stylesheet.dto.DilaStyleSheet;
import fr.paris.lutece.plugins.dila.utils.ListUtils;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;

import java.util.Locale;


/**
 *
 * class DilaStyleSheetResourceIdService
 *
 */
public class DilaStyleSheetResourceIdService extends ResourceIdService
{
    /** Permission for creating a dila stylesheet */
    public static final String PERMISSION_CREATE_STYLESHEET = "CREATE_STYLESHEET";

    /** Permission for deleting a dila stylesheet */
    public static final String PERMISSION_DELETE_STYLESHEET = "DELETE_STYLESHEET";

    /** Permission for modifying a dila stylesheet */
    public static final String PERMISSION_MODIFY_STYLESHEET = "MODIFY_STYLESHEET";

    /** Permission for viewing a dila stylesheet */
    public static final String PERMISSION_VIEW_STYLESHEET = "VIEW_STYLESHEET";
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "dila.permission.label.resourceType";
    private static final String PROPERTY_LABEL_CREATE_STYLESHEET = "dila.permission.label.create_stylesheet";
    private static final String PROPERTY_LABEL_DELETE_STYLESHEET = "dila.permission.label.delete_stylesheet";
    private static final String PROPERTY_LABEL_MODIFY_STYLESHEET = "dila.permission.label.modify_stylesheet";
    private static final String PROPERTY_LABEL_VIEW_STYLESHEET = "dila.permission.label.view_stylesheet";
    private IDilaStyleSheetService _dilaStyleSheetService = SpringContextService.getBean( "dilaStyleSheetService" );

    /** Creates a new instance of DilaStyleSheetResourceIdService */
    public DilaStyleSheetResourceIdService(  )
    {
        setPluginName( DilaPlugin.PLUGIN_NAME );
    }

    /**
     * Initializes the service
     */
    public void register(  )
    {
        ResourceType rt = new ResourceType(  );
        rt.setResourceIdServiceClass( DilaStyleSheetResourceIdService.class.getName(  ) );
        rt.setPluginName( DilaPlugin.PLUGIN_NAME );
        rt.setResourceTypeKey( DilaStyleSheet.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = new Permission(  );
        p.setPermissionKey( PERMISSION_CREATE_STYLESHEET );
        p.setPermissionTitleKey( PROPERTY_LABEL_CREATE_STYLESHEET );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_MODIFY_STYLESHEET );
        p.setPermissionTitleKey( PROPERTY_LABEL_MODIFY_STYLESHEET );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_VIEW_STYLESHEET );
        p.setPermissionTitleKey( PROPERTY_LABEL_VIEW_STYLESHEET );
        rt.registerPermission( p );

        p = new Permission(  );
        p.setPermissionKey( PERMISSION_DELETE_STYLESHEET );
        p.setPermissionTitleKey( PROPERTY_LABEL_DELETE_STYLESHEET );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * Returns a list of dila stylesheet resource ids
     * @param locale The current locale
     * @return A list of resource ids
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        return ListUtils.toReferenceList( _dilaStyleSheetService.getDilaStyleSheetList( null, null ), "id",
            "description", null );
    }

    /**
     * Returns the Title of a given resource
     * @param strId The Id of the resource
     * @param locale The current locale
     * @return The Title of a given resource
     */
    public String getTitle( String strId, Locale locale )
    {
        int nIdStyleSheet = -1;

        try
        {
            nIdStyleSheet = Integer.parseInt( strId );
        }
        catch ( NumberFormatException ne )
        {
            AppLogService.error( ne );
        }

        DilaStyleSheet styleSheet = _dilaStyleSheetService.findByPrimaryKey( nIdStyleSheet );

        return styleSheet.getDescription(  );
    }
}
