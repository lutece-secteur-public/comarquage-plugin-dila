/**
 * 
 */
package fr.paris.lutece.plugins.dila.service;

import java.util.Locale;


/**
 * Dila cache service
 */
public interface IDilaCacheService
{

    /**
     * Get html page for give key
     * @param strCacheKey the cache key
     * @param locale the local
     * @return the html code
     */
    String getRessource( String strCacheKey, Locale locale );
}
