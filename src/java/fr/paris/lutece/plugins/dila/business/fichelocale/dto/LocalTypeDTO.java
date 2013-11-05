package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

import java.io.Serializable;


/**
 * DTO for local type object
 */
public class LocalTypeDTO implements Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 8251300174468562167L;

    /**
     * The id
     */
    private Long _lId;

    /** The label */
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
     * @return the _strLabel
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
