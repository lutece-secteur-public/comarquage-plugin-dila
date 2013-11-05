package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;

import java.io.Serializable;


/**
 * DTO for Local folder object
 */
public class LocalFolderDTO implements Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 47780930283205463L;

    /**
     * The id of folder
     */
    private Long _lId;

    /**
     * The id of theme parent
     */
    private String _strParentThemeId;

    /**
     * The title of theme parent
     */
    private String _strParentThemeTitle;

    /**
     * The id of sibling folder
     */
    private String _strSiblingFolderId;

    /**
     * The title of sibling folder
     */
    private String _strSiblingFolderTitle;

    /**
     * The position of sibling folder (Before / After)
     */
    private Integer _nPosition;

    /**
     * The presentation
     */
    private String _strPresentation;

    /**
     * The linked {@link LocalDTO} resource
     */
    private LocalDTO _localDTO;

    /**
     * Default constructor
     */
    public LocalFolderDTO( )
    {
        LocalTypeDTO type = new LocalTypeDTO( );
        type.setId( DilaLocalTypeEnum.FOLDER.getId( ) );
        _localDTO = new LocalDTO( type );
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
     * @return the _strParentThemeId
     */
    public String getParentThemeId( )
    {
        return _strParentThemeId;
    }

    /**
     * @param strParentThemeId the _strParentThemeId to set
     */
    public void setParentThemeId( String strParentThemeId )
    {
        this._strParentThemeId = strParentThemeId;
    }

    /**
     * @return the _strSiblingFolderId
     */
    public String getSiblingFolderId( )
    {
        return _strSiblingFolderId;
    }

    /**
     * @param strSiblingFolderId the _strSiblingFolderId to set
     */
    public void setSiblingFolderId( String strSiblingFolderId )
    {
        this._strSiblingFolderId = strSiblingFolderId;
    }

    /**
     * @return the _nPosition
     */
    public Integer getPosition( )
    {
        return _nPosition;
    }

    /**
     * @param nPosition the _nPosition to set
     */
    public void setPosition( Integer nPosition )
    {
        this._nPosition = nPosition;
    }

    /**
     * @return the _strPresentation
     */
    public String getPresentation( )
    {
        return _strPresentation;
    }

    /**
     * @param strPresentation the _strPresentation to set
     */
    public void setPresentation( String strPresentation )
    {
        this._strPresentation = strPresentation;
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
     * @return the _strParentThemeTitle
     */
    public String getParentThemeTitle( )
    {
        return _strParentThemeTitle;
    }

    /**
     * @param strParentThemeTitle the _strParentThemeTitle to set
     */
    public void setParentThemeTitle( String strParentThemeTitle )
    {
        this._strParentThemeTitle = strParentThemeTitle;
    }

    /**
     * @return the _strSiblingFolderTitle
     */
    public String getSiblingFolderTitle( )
    {
        return _strSiblingFolderTitle;
    }

    /**
     * @param strSiblingFolderTitle the _strSiblingFolderTitle to set
     */
    public void setSiblingFolderTitle( String strSiblingFolderTitle )
    {
        this._strSiblingFolderTitle = strSiblingFolderTitle;
    }

}
