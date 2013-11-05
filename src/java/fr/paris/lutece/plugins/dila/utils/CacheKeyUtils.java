/**
 * 
 */
package fr.paris.lutece.plugins.dila.utils;

import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;

import org.apache.commons.lang.StringUtils;


/**
 * Class used to generate and decode cache key
 * 
 */
public final class CacheKeyUtils
{
    /**
     * Private constructor
     */
    private CacheKeyUtils( )
    {

    }

    /**
     * Generate cache key
     * @param lCategoryId the category id
     * @param strCardId the card id
     * @return the generated key
     */
    public static String generateCacheKey( Long lCategoryId, String strCardId )
    {
        StringBuilder sbKey = new StringBuilder( );
        sbKey.append( lCategoryId ).append( DilaConstants.CACHE_KEY_SEPARATOR ).append( strCardId );
        return sbKey.toString( );
    }

    /**
     * Get category part of cache key
     * @param strKey the key to split
     * @return the category id of cache key
     */
    public static Long getCategoryFromCacheKey( String strKey )
    {
        Long catId = null;
        if ( StringUtils.isNotBlank( strKey ) )
        {
            String[] splitKey = strKey.split( DilaConstants.CACHE_KEY_SEPARATOR );
            if ( splitKey.length >= 1 && StringUtils.isNumeric( splitKey[0] ) )
            {
                catId = Long.valueOf( splitKey[0] );
            }
        }
        return catId;
    }

    /**
     * Get card id part of cache key
     * @param strKey the key to split
     * @return the card id of cache key
     */
    public static String getCardIdFromCacheKey( String strKey )
    {
        String cardId = null;
        if ( StringUtils.isNotBlank( strKey ) )
        {
            String[] splitKey = strKey.split( DilaConstants.CACHE_KEY_SEPARATOR );
            if ( splitKey.length == 2 )
            {
                cardId = splitKey[1];
            }
        }
        return cardId;
    }
}
