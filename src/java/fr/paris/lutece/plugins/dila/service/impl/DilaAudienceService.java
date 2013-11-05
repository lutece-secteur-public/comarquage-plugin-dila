package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IAudienceDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;
import fr.paris.lutece.plugins.dila.service.IDilaAudienceService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * The implementation of {@link IDilaAudienceService}
 */
public class DilaAudienceService implements IDilaAudienceService, Serializable
{
    /** The serial ID */
    private static final long serialVersionUID = -293259001821656504L;

    @Inject
    @Named( "dilaAudienceDAO" )
    private IAudienceDAO _dilaAudienceDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AudienceDTO> findAll( )
    {
        return _dilaAudienceDAO.findAll( );
    }

}
