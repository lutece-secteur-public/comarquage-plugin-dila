/**
 * 
 */
package fr.paris.lutece.plugins.dila.business.enums;

/**
 * 
 */
public enum AudienceCategoryEnum
{
    INDIVIDUALS( 1L, "Particuliers" ), ASSOCIATIONS( 2L, "Associations" ), PROFESSIONNALS( 3L, "Professionnels" );

    private Long _lId;
    private String _strLabel;

    /**
     * Constructor
     * @param lId the id
     * @param strLabel the label
     */
    private AudienceCategoryEnum( Long lId, String strLabel )
    {
        this._lId = lId;
        this._strLabel = strLabel;
    }

    /**
     * Get the enum from its id
     * @param lId the id to search
     * @return the corresponding enum
     */
    public static AudienceCategoryEnum fromId( Long lId )
    {
        for ( AudienceCategoryEnum e : AudienceCategoryEnum.values( ) )
        {
            if ( e.getId( ).equals( lId ) )
            {
                return e;
            }
        }
        return null;
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
     * Get the enum from its label
     * @param strLabel the label
     * @return the corresponding enum, null if not found
     */
    public static AudienceCategoryEnum fromLabel( String strLabel )
    {
        for ( AudienceCategoryEnum e : AudienceCategoryEnum.values( ) )
        {
            if ( e.getLabel( ).equalsIgnoreCase( strLabel ) )
            {
                return e;
            }
        }
        return null;
    }
}
