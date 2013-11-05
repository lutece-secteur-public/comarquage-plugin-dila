package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.stylesheet.dao.IContentTypeDAO;
import fr.paris.lutece.plugins.dila.business.stylesheet.dto.ContentType;
import fr.paris.lutece.plugins.dila.service.IContentTypeService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Implements the access methods of a ContentType
 */
public class ContentTypeService implements IContentTypeService, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 6981021939747778320L;

    @Inject
    @Named( "contentTypeDAO" )
    private IContentTypeDAO _contentTypeDAO;

    @Override
    public List<ContentType> getContentTypes( )
    {
        return _contentTypeDAO.getContentTypes( );
    }

    @Override
    public ContentType findByPrimaryKey( Integer nIdTypeContenu )
    {
        return _contentTypeDAO.findByPrimaryKey( nIdTypeContenu );
    }

    @Override
    public List<ContentType> getContentTypesWithoutAssociatedStyleSheet( Integer nIdStylesheet )
    {
        return _contentTypeDAO.getContentTypesWithoutAssociatedStyleSheet( nIdStylesheet );
    }
}
