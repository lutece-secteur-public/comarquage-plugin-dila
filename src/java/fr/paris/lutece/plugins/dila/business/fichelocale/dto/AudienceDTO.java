package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

/**
 * DTO for Audience object
 */
public class AudienceDTO
{

    /**
     * The id of audience
     */
    private Long _lId;

    /**
     * The label of audience
     */
    private String _strLabel;

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
     * @return the _strName
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * @param strLabel the _strLabel to set
     */
    public void setLabel( String strLabel )
    {
        this._strLabel = strLabel;
    }
}
