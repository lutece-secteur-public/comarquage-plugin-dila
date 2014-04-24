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
package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO for Local object
 */
public class LocalDTO implements Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -1191940287861319929L;

    /**
     * The id
     */
    private Long _lId;

    /**
     * Title of resource
     */
    private String _strTitle;

    /**
     * Name of author
     */
    private String _strAuthor;

    /**
     * The breadcrumb of resource
     */
    private String _strBreadCrumb;

    /**
     * The breadcrumb of resource
     */
    private String _strDisplayPath;

    /**
     * The xml content
     */
    private String _strXml;

    /**
     * The id of audience
     */
    private Long _lIdAudience;

    /**
     * The type of resource (Card or Folder)
     */
    private LocalTypeDTO _type;

    /**
     * The creation date
     */
    private Date _creationDate;

    /**
     * Default constructor
     */
    public LocalDTO(  )
    {
    }

    /**
     * Create a local resource by init its _type
     * @param type the type of resource
     */
    public LocalDTO( LocalTypeDTO type )
    {
        _type = type;
    }

    /**
     * @return the _lId
     */
    public Long getId(  )
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
     * @return the _strTitle
     */
    public String getTitle(  )
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
     * @return the _strAuthor
     */
    public String getAuthor(  )
    {
        return _strAuthor;
    }

    /**
     * @param strAuthor the _strAuthor to set
     */
    public void setAuthor( String strAuthor )
    {
        this._strAuthor = strAuthor;
    }

    /**
     * @return the _strXml
     */
    public String getXml(  )
    {
        return _strXml;
    }

    /**
     * @param strXml the _strXml to set
     */
    public void setXml( String strXml )
    {
        this._strXml = strXml;
    }

    /**
     * @return the _lIdAudience
     */
    public Long getIdAudience(  )
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
     * @return the _type
     */
    public LocalTypeDTO getType(  )
    {
        return _type;
    }

    /**
     * @param type the _type to set
     */
    public void setType( LocalTypeDTO type )
    {
        this._type = type;
    }

    /**
     * @return the _strBreadCrumb
     */
    public String getBreadCrumb(  )
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
     * @return the _strDisplayPath
     */
    public String getDisplayPath(  )
    {
        return _strDisplayPath;
    }

    /**
     * @param strDisplayPath the _strDisplayPath to set
     */
    public void setDisplayPath( String strDisplayPath )
    {
        this._strDisplayPath = strDisplayPath;
    }

    /**
     * @return the _creationDate
     */
    public Date getCreationDate(  )
    {
        return (Date) _creationDate.clone(  );
    }

    /**
     * @param creationDate the _creationDate to set
     */
    public void setCreationDate( Date creationDate )
    {
        this._creationDate = (Date) creationDate.clone(  );
    }

    /**
     * Hack to order by type label
     * @return the type label {@link LocalTypeDTO#getLabel()}
     */
    public String getTypeLabel(  )
    {
        return getType(  ).getLabel(  );
    }
}
