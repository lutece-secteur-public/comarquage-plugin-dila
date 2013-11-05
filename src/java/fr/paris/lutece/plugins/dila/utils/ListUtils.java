/*
 * Copyright (c) 2002-2010, Mairie de Paris
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

import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;


/**
 * Utilitaire servant à la manipulation des listes
 * 
 */
public final class ListUtils
{

    private static final Logger LOGGER = Logger.getLogger( ListUtils.class );
    private static final String PROPERTY_LIST_SEPARATOR = ";";

    /**
     * Instantiates a new list utils.
     */
    private ListUtils( )
    {
        // does nothing
    }

    /**
     * Return.
     * 
     * @param propertyKey the property key
     * @return the property list
     */
    public static List<String> getPropertyList( String propertyKey )
    {
        String property = AppPropertiesService.getProperty( propertyKey );
        if ( property != null )
        {
            String[] items = property.split( PROPERTY_LIST_SEPARATOR );
            if ( items != null )
            {
                return Arrays.asList( items );
            }
        }
        return null;
    }

    /**
     * Convert a {@link List} to a {@link ReferenceList}
     * 
     * @param list
     *            list to convert
     * @param key
     *            the property to use as a key for {@link ReferenceList}
     * @param value
     *            the property to use as a value for {@link ReferenceList}
     * @param firstItem
     *            the first item in the {@link ReferenceList}
     * @return the {@link ReferenceList} with data from given list
     */
    public static ReferenceList toReferenceList( List<?> list, String key, String value, String firstItem )
    {
        ReferenceList referenceList = new ReferenceList( );
        String valueKey;
        String valueValue;

        try
        {
            if ( firstItem != null )
            {
                referenceList.addItem( "-1", firstItem );
            }

            for ( Object element : list )
            {
                valueKey = BeanUtils.getSimpleProperty( element, key );
                valueValue = BeanUtils.getSimpleProperty( element, value );
                referenceList.addItem( valueKey, valueValue );
            }
        }
        catch ( IllegalAccessException e )
        {
            LOGGER.warn( "Error creating a combo list : " + e.getMessage( ), e );
        }
        catch ( InvocationTargetException e )
        {
            LOGGER.warn( "Error creating a combo list : " + e.getMessage( ), e );
        }
        catch ( NoSuchMethodException e )
        {
            LOGGER.warn( "Error creating a combo list : " + e.getMessage( ), e );
        }
        catch ( Exception e )
        {
            LOGGER.warn( "Error creating a combo list : " + e.getMessage( ), e );
        }

        return referenceList;
    }
}
