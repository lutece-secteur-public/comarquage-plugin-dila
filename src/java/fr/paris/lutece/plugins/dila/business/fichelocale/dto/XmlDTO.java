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
package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * DTO for dila_xml table
 */
public class XmlDTO implements Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 6791880867164758677L;

    /**
     * The id
     */
    private Long _lId;

    /**
     * The id xml
     */
    private String _strIdXml;

    /**
     * The title
     */
    private String _strTitle;

    /**
     * The breadcrumb of resource
     */
    private String _strBreadCrumb;

    /**
     * The id of audience
     */
    private Long _lIdAudience;

    /**
     * Type resource
     */
    private String _strResourceType;

    /**
     * Date modif
     */
    private Date _creationDate;

    /**
     * Date modif
     */
    private Date _modificationDate;

    /**
     * @return the _lId
     */
    public Long getId( )
    {
        return _lId;
    }

    /**
     * @param lId the _lId to set
     */
    public void setId( Long lId )
    {
        this._lId = lId;
    }

    /**
     * @return the _strIdXml
     */
    public String getIdXml( )
    {
        return _strIdXml;
    }

    /**
     * @param strIdXml the _strIdXml to set
     */
    public void setIdXml( String strIdXml )
    {
        this._strIdXml = strIdXml;
    }

    /**
     * @return the _strTitle
     */
    public String getTitle( )
    {
        return _strTitle;
    }

    /**
     * @param strTitle the _strTitle to set
     */
    public void setTitle( String strTitle )
    {
        this._strTitle = strTitle;
    }

    /**
     * @return the _strBreadCrumb
     */
    public String getBreadCrumb( )
    {
        return _strBreadCrumb;
    }

    /**
     * @param strBreadCrumb the _strBreadCrumb to set
     */
    public void setBreadCrumb( String strBreadCrumb )
    {
        this._strBreadCrumb = strBreadCrumb;
    }

    /**
     * @return the _lIdAudience
     */
    public Long getIdAudience( )
    {
        return _lIdAudience;
    }

    /**
     * @param lIdAudience the _lIdAudience to set
     */
    public void setIdAudience( Long lIdAudience )
    {
        this._lIdAudience = lIdAudience;
    }

    /**
     * @return the _strResourceType
     */
    public String getResourceType( )
    {
        return _strResourceType;
    }

    /**
     * @param strResourceType the _strResourceType to set
     */
    public void setResourceType( String strResourceType )
    {
        this._strResourceType = strResourceType;
    }

    /**
     * @return the _modificationDate
     */
    public Date getModificationDate( )
    {
        if ( _modificationDate != null )
        {
            return (Date) _modificationDate.clone( );
        }
        else
        {
            return null;
        }
    }

    /**
     * @param modificationDate the _modificationDate to set
     */
    public void setModificationDate( Date modificationDate )
    {
        if ( modificationDate != null )
        {
            this._modificationDate = (Date) modificationDate.clone( );
        }
    }

    /**
     * @return the _creationDate
     */
    public Date getCreationDate( )
    {
        return (Date) _creationDate.clone( );
    }

    /**
     * @param creationDate the _creationDate to set
     */
    public void setCreationDate( Date creationDate )
    {
        this._creationDate = (Date) creationDate.clone( );
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + ( ( _lIdAudience == null ) ? 0 : _lIdAudience.hashCode( ) );
        result = ( prime * result ) + ( ( _strIdXml == null ) ? 0 : _strIdXml.hashCode( ) );

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null )
        {
            return false;
        }

        if ( getClass( ) != obj.getClass( ) )
        {
            return false;
        }

        XmlDTO other = (XmlDTO) obj;

        if ( _lIdAudience == null )
        {
            if ( other._lIdAudience != null )
            {
                return false;
            }
        }
        else if ( !_lIdAudience.equals( other._lIdAudience ) )
        {
            return false;
        }

        if ( _strIdXml == null )
        {
            if ( other._strIdXml != null )
            {
                return false;
            }
        }
        else if ( !_strIdXml.equals( other._strIdXml ) )
        {
            return false;
        }

        return true;
    }
}
