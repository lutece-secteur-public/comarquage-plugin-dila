package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalFolderLinkDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalFolderLinkService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Implementation of {@link IDilaLocalFolderLinkService}
 */
public class DilaLocalFolderLinkService implements IDilaLocalFolderLinkService, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 7532352392539939302L;

    @Inject
    @Named( "dilaLocalFolderLinkDAO" )
    private ILocalFolderLinkDAO _dilaLocalFolderLinkDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( LocalFolderLinkDTO link )
    {
        _dilaLocalFolderLinkDAO.insert( link );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByFolderId( Long localFolderId )
    {
        _dilaLocalFolderLinkDAO.deleteByFolderId( localFolderId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalFolderLinkDTO> findByFolderId( Long id )
    {
        return _dilaLocalFolderLinkDAO.findByFolderId( id );
    }

}
