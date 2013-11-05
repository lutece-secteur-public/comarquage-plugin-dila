package fr.paris.lutece.plugins.dila.service;

/**
 * Eunm for type resource
 * @author rputegnat
 */
public enum DilaLocalTypeEnum
{
    CARD( 1L, "Fiche" ), FOLDER( 2L, "Dossier" );

    /**
     * The enum id
     */
    private Long _lId;

    /**
     * The enum label
     */
    private String _strLabel;

    /**
     * Constructor
     * @param lId the id
     * @param strLabel the label
     */
    private DilaLocalTypeEnum( Long lId, String strLabel )
    {
        _lId = lId;
        _strLabel = strLabel;
    }

    /**
     * @return the _lId
     */
    public Long getId( )
    {
        return _lId;
    }

    /**
     * @return the _strLabel
     */
    public String getLabel( )
    {
        return _strLabel;
    }

    /**
     * Get the enum from its id
     * @param lId the id to search
     * @return the corresponding enum
     */
    public static DilaLocalTypeEnum fromId( Long lId )
    {
        for ( DilaLocalTypeEnum e : DilaLocalTypeEnum.values( ) )
        {
            if ( e.getId( ).compareTo( lId ) == 0 )
            {
                return e;
            }
        }
        return null;
    }
}
