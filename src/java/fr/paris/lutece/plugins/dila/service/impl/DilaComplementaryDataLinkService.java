package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.IComplementaryDataLinkDAO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataLinkService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Implementatino of {@link IDilaComplementaryDataLinkService}
 */
public class DilaComplementaryDataLinkService implements IDilaComplementaryDataLinkService, Serializable
{
    /** The serial ID */
    private static final long serialVersionUID = 4329993415003648063L;

    @Inject
    @Named( "dilaComplementaryDataLinkDAO" )
    private IComplementaryDataLinkDAO _dilaComplementaryDataLinkDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( ComplementaryDataLinkDTO dto )
    {
        _dilaComplementaryDataLinkDAO.insert( dto );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFromComplementaryData( Long lId, ComplementaryLinkTypeEnum type )
    {
        _dilaComplementaryDataLinkDAO.delete( lId, type );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ComplementaryDataLinkDTO> findByDataId( Long lComplementaryDataId, ComplementaryLinkTypeEnum type )
    {
        return _dilaComplementaryDataLinkDAO.findByDataId( lComplementaryDataId, type );
    }

}
