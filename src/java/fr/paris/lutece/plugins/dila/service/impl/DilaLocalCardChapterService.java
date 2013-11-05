package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalCardChapterDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalCardChapterService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Implementation of {@link IDilaLocalCardChapterService}
 */
public class DilaLocalCardChapterService implements IDilaLocalCardChapterService, Serializable
{
    /** The serial ID */
    private static final long serialVersionUID = -6830472344884791089L;

    @Inject
    @Named( "dilaLocalCardChapterDAO" )
    private ILocalCardChapterDAO _dilaLocalCardChapterDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( LocalCardChapterDTO chapter )
    {
        _dilaLocalCardChapterDAO.create( chapter );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByCardId( Long lLocalCardId )
    {
        _dilaLocalCardChapterDAO.deleteByCardId( lLocalCardId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalCardChapterDTO> findByCardId( Long lCardId )
    {
        return _dilaLocalCardChapterDAO.findByCardId( lCardId );
    }

}
