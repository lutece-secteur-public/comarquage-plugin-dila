package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto;

import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;


/**
 * DTO for dila_ donnees_complementaires table
 */
public abstract class ComplementaryDataLinkDTO
{
    /**
     * The database id
     */
    private Long _lId;

    /**
     * The title
     */
    private String _strTitle;

    /**
     * The url
     */
    private String _strURL;

    /**
     * The position
     */
    private Integer _nPosition;

    /**
     * The id to linked complementary data
     */
    private Long _lIdComplementaryData;

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
     * @return the _strURL
     */
    public String getURL( )
    {
        return _strURL;
    }

    /**
     * @param strURL the _strURL to set
     */
    public void setURL( String strURL )
    {
        this._strURL = strURL;
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
     * @return the _lIdComplementaryData
     */
    public Long getIdComplementaryData( )
    {
        return _lIdComplementaryData;
    }

    /**
     * @param lIdComplementaryData the _lIdComplementaryData to set
     */
    public void setIdComplementaryData( Long lIdComplementaryData )
    {
        this._lIdComplementaryData = lIdComplementaryData;
    }

    /**
     * @return the type of link
     */
    public abstract ComplementaryLinkTypeEnum getType( );
}
