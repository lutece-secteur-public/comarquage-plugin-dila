package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

/**
 * DTO for Link local folder
 */
public class LocalFolderLinkDTO
{
    /**
     * Id of the resource
     */
    private Long _lId;

    /**
     * Title of the resource
     */
    private String _strTitle;

    /**
     * Position of link
     */
    private Integer _nPosition;

    /**
     * The card id
     */
    private String _strCardId;

    /**
     * The local folder id
     */
    private Long _lLocalFolderId;

    /**
     * The card title
     */
    private String _strCardTitle;

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
     * @return the position
     */
    public Integer getPosition( )
    {
        return _nPosition;
    }

    /**
     * @param nPosition the position to set
     */
    public void setPosition( Integer nPosition )
    {
        this._nPosition = nPosition;
    }

    /**
     * @return the _strCardId
     */
    public String getCardId( )
    {
        return _strCardId;
    }

    /**
     * @param strCardId the _strCardId to set
     */
    public void setCardId( String strCardId )
    {
        this._strCardId = strCardId;
    }

    /**
     * @return the _lLocalFolderId
     */
    public Long getLocalFolderId( )
    {
        return _lLocalFolderId;
    }

    /**
     * @param lLocalFolderId the _lLocalFolderId to set
     */
    public void setLocalFolderId( Long lLocalFolderId )
    {
        this._lLocalFolderId = lLocalFolderId;
    }

    /**
     * @return the _strCardTitle
     */
    public String getCardTitle( )
    {
        return _strCardTitle;
    }

    /**
     * @param strCardTitle the _strCardTitle to set
     */
    public void setCardTitle( String strCardTitle )
    {
        _strCardTitle = strCardTitle;
    }
}
