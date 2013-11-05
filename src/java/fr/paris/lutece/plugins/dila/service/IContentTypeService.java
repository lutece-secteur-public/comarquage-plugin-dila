package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.stylesheet.dto.ContentType;

import java.util.List;


/**
 * The Interface IContentTypeService.
 */
public interface IContentTypeService
{
    /**
     * Return the list of all ContentType
     * @return ContentType list
     */
    List<ContentType> getContentTypes( );

    /**
     * Return the ContentType by id
     * @param nContentTypeId the ContentType id
     * @return ContentType
     */
    ContentType findByPrimaryKey( Integer nContentTypeId );

    /**
     * Return the list of all ContentType without associated stylesheet
     * 
     * @param nIdStylesheet stylesheet id, if != 0 the method will return all
     *            ContentType without associated stylesheet plus the ContentType
     *            associate to the specific id stylesheet
     * @return ContentType list
     */
    List<ContentType> getContentTypesWithoutAssociatedStyleSheet( Integer nIdStylesheet );
}
