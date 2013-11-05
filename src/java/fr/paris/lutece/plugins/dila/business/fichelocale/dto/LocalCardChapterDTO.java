package fr.paris.lutece.plugins.dila.business.fichelocale.dto;

/**
 * DTO for Chapitre Fiche Locale object
 * @author rputegnat
 */
public class LocalCardChapterDTO
{
    /**
     * The id of resource
     */
    private Long _lLocalCardChapterId;

    /**
     * The chapter title
     */
    private String _strTitle;

    /**
     * The chapter content
     */
    private String _strContent;

    /**
     * The position of chapter
     */
    private int _nPosition;

    /**
     * The linked {@link LocalCardDTO}
     */
    private LocalCardDTO _localCard;

    /**
     * @return the _lLocalCardChapterId
     */
    public Long getLocalCardChapterId( )
    {
        return _lLocalCardChapterId;
    }

    /**
     * @param lLocalCardChapterId the _lLocalCardChapterId to set
     */
    public void setLocalCardChapterId( Long lLocalCardChapterId )
    {
        this._lLocalCardChapterId = lLocalCardChapterId;
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
     * @return the _strContent
     */
    public String getContent( )
    {
        return _strContent;
    }

    /**
     * @param strContent the _strContent to set
     */
    public void setContent( String strContent )
    {
        this._strContent = strContent;
    }

    /**
     * @return the position
     */
    public int getPosition( )
    {
        return _nPosition;
    }

    /**
     * @param nPosition the position to set
     */
    public void setPosition( int nPosition )
    {
        this._nPosition = nPosition;
    }

    /**
     * @return the _localCard
     */
    public LocalCardDTO getLocalCard( )
    {
        return _localCard;
    }

    /**
     * @param localCard the _localCard to set
     */
    public void setLocalCard( LocalCardDTO localCard )
    {
        this._localCard = localCard;
    }

}
