/**
 *
 */
package fr.paris.lutece.plugins.dila.business.enums;


/**
 * Enum for all content type
 *
 */
public enum ContentTypeEnum
{CARD( 1L ),
    NODE( 2L ),
    RESOURCES( 3L ),
    THEMES( 4L ),
    PROFESSIONAL_THEMES( 5L ),
    PIVOT( 6L ),
    LOCAL( 7L );

    /**
     * The content type id
     */
    private Long _lId;

    /**
     * Constructor
     * @param lId the id to set
     */
    private ContentTypeEnum( Long lId )
    {
        _lId = lId;
    }

    /**
     * @return the _lId
     */
    public Long getId(  )
    {
        return _lId;
    }

    /**
     * Get ContentTypeEnum from id
     * @param id the id to get
     * @return the corresponding enum, null if not found
     */
    public static ContentTypeEnum fromId( Long id )
    {
        for ( ContentTypeEnum e : ContentTypeEnum.values(  ) )
        {
            if ( e.getId(  ).equals( id ) )
            {
                return e;
            }
        }

        return null;
    }
}
