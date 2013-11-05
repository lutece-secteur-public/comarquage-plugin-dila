/**
 * 
 */
package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;

import java.io.Serializable;


/**
 * @author abataille
 */
public class ComplementaryDataDTO implements Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -3811416313997827545L;

    /**
     * The database id
     */
    private Long _lId;

    /**
     * Content of bottom block
     */
    private String _strBottomBlock;

    /**
     * Content of column block
     */
    private String _strColumnBlock;
    /**
     * The database audience id
     */
    private Long _lIdAudience;

    /**
     * The linked card
     */
    private XmlDTO _card;

    /**
     * Default constructor
     */
    public ComplementaryDataDTO( )
    {
        _card = new XmlDTO( );
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
     * @return the _strBottomBlock
     */
    public String getBottomBlock( )
    {
        return _strBottomBlock;
    }

    /**
     * @param strBottomBlock the _strBottomBlock to set
     */
    public void setBottomBlock( String strBottomBlock )
    {
        this._strBottomBlock = strBottomBlock;
    }

    /**
     * @return the _strColumnBlock
     */
    public String getColumnBlock( )
    {
        return _strColumnBlock;
    }

    /**
     * @param strColumnBlock the _strColumnBlock to set
     */
    public void setColumnBlock( String strColumnBlock )
    {
        this._strColumnBlock = strColumnBlock;
    }

    /**
     * @return the _card
     */
    public XmlDTO getCard( )
    {
        return _card;
    }

    /**
     * @param card the _card to set
     */
    public void setCard( XmlDTO card )
    {
        this._card = card;
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
     * Hack to order by linked card idXml
     * 
     * @return the linked card idXml {@link XmlDTO#getIdXml()}
     */
    public String getCardIdXml( )
    {
        return getCard( ).getIdXml( );
    }

    /**
     * Hack to order by linked card title
     * 
     * @return the linked card title {@link XmlDTO#getTitle()}
     */
    public String getCardTitle( )
    {
        return getCard( ).getTitle( );
    }
}
