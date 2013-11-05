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
package fr.paris.lutece.plugins.dila.business.stylesheet.dto;

import fr.paris.lutece.plugins.dila.business.stylesheet.DilaStyleSheetAction;
import fr.paris.lutece.portal.business.stylesheet.StyleSheet;
import fr.paris.lutece.portal.service.rbac.RBACResource;

import java.util.List;


/**
 * This class represents business Dila StyleSheet objects
 */
public class DilaStyleSheet extends StyleSheet implements RBACResource
{
    public static final String RESOURCE_TYPE = "DILA_STYLESHEET";
    private ContentType _contentType = new ContentType( );
    private List<DilaStyleSheetAction> _listActions;

    /**
     * @param contentType the _contentType to set
     */
    public void setContentType( ContentType contentType )
    {
        this._contentType = contentType;
    }

    /**
     * @return the _contentType
     */
    public ContentType getContentType( )
    {
        return _contentType;
    }

    /**
     * Return the contentType label to sort the list
     * 
     * @return String label
     */
    public String getContentTypeLabel( )
    {
        return this._contentType.getLabel( );
    }

    /**
     * @param listActions the listActions to set
     */
    public void setListActions( List<DilaStyleSheetAction> listActions )
    {
        this._listActions = listActions;
    }

    /**
     * @return the _listActions
     */
    public List<DilaStyleSheetAction> getListActions( )
    {
        return _listActions;
    }

    /**
     * RBAC resource implementation
     * @return The resource type code
     */
    public String getResourceTypeCode( )
    {
        return RESOURCE_TYPE;
    }

    /**
     * RBAC resource implementation
     * @return The resourceId
     */
    public String getResourceId( )
    {
        return "" + this.getId( );
    }
}
