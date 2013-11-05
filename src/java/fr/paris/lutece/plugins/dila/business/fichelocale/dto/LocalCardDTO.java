package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;

import java.io.Serializable;


/**
 * DTO for local card object
 */
public class LocalCardDTO implements Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -9030684250164585235L;

    /** The id */
    private Long _lId;

    /** The parent folder id */
    private String _strParentFolderId;

    /** The parent folder title */
    private String _strParentFolderTitle;

    /** The local parent folder */
    private LocalFolderDTO _localParentFolder;

    /** the national parent folder */
    private XmlDTO _nationalParentFolder;

    /** The sibling card. */
    private String _strSiblingCardId;

    /** The sibling card title */
    private String _strSiblingCardTitle;

    /** The position. */
    private Integer _nPosition;

    /** The linked {@link LocalDTO} resource */
    private LocalDTO _localDTO;

    /**
     * Default contructor. Init the {@link LocalDTO} with card type
     */
    public LocalCardDTO( )
    {
        LocalTypeDTO type = new LocalTypeDTO( );
        type.setId( DilaLocalTypeEnum.CARD.getId( ) );
        _localDTO = new LocalDTO( type );

        _localParentFolder = new LocalFolderDTO( );
        _nationalParentFolder = new XmlDTO( );
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
     * @return the _strParentFolderId
     */
    public String getParentFolderId( )
    {
        return _strParentFolderId;
    }

    /**
     * @param strParentFolderId the _strParentFolderId to set
     */
    public void setParentFolderId( String strParentFolderId )
    {
        this._strParentFolderId = strParentFolderId;
    }

    /**
     * @return the _strSiblingCardId
     */
    public String getSiblingCardId( )
    {
        return _strSiblingCardId;
    }

    /**
     * @param strSiblingCardId the _strSiblingCardId to set
     */
    public void setSiblingCardId( String strSiblingCardId )
    {
        this._strSiblingCardId = strSiblingCardId;
    }

    /**
     * @return the position
     */
    public Integer getPosition( )
    {
        return _nPosition;
    }

    /**
     * @param position the position to set
     */
    public void setPosition( Integer position )
    {
        this._nPosition = position;
    }

    /**
     * @return the _localDTO
     */
    public LocalDTO getLocalDTO( )
    {
        return _localDTO;
    }

    /**
     * @param localDTO the _localDTO to set
     */
    public void setLocalDTO( LocalDTO localDTO )
    {
        this._localDTO = localDTO;
    }

    /**
     * @return the _localParentFolder
     */
    public LocalFolderDTO getLocalParentFolder( )
    {
        return _localParentFolder;
    }

    /**
     * @param localParentFolder the _localParentFolder to set
     */
    public void setLocalParentFolder( LocalFolderDTO localParentFolder )
    {
        this._localParentFolder = localParentFolder;
    }

    /**
     * @return the _nationalParentFolder
     */
    public XmlDTO getNationalParentFolder( )
    {
        return _nationalParentFolder;
    }

    /**
     * @param nationalParentFolder the _nationalParentFolder to set
     */
    public void setNationalParentFolder( XmlDTO nationalParentFolder )
    {
        this._nationalParentFolder = nationalParentFolder;
    }

    /**
     * @return the _strParentFolderTitle
     */
    public String getParentFolderTitle( )
    {
        return _strParentFolderTitle;
    }

    /**
     * @param strParentFolderTitle the _strParentFolderTitle to set
     */
    public void setParentFolderTitle( String strParentFolderTitle )
    {
        this._strParentFolderTitle = strParentFolderTitle;
    }

    /**
     * @return the _strSiblingCardTitle
     */
    public String getSiblingCardTitle( )
    {
        return _strSiblingCardTitle;
    }

    /**
     * @param strSiblingCardTitle the _strSiblingCardTitle to set
     */
    public void setSiblingCardTitle( String strSiblingCardTitle )
    {
        this._strSiblingCardTitle = strSiblingCardTitle;
    }
}
