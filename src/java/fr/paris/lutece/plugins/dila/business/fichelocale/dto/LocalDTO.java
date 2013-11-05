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
    public LocalDTO( )
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
     * @return the _strAuthor
     */
    public String getAuthor( )
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
    public String getXml( )
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
     * @return the _type
     */
    public LocalTypeDTO getType( )
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
     * @return the _strDisplayPath
     */
    public String getDisplayPath( )
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

    /**
     * Hack to order by type label
     * @return the type label {@link LocalTypeDTO#getLabel()}
     */
    public String getTypeLabel( )
    {
        return getType( ).getLabel( );
    }
}
