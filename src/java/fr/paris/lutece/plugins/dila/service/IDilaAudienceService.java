package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;

import java.util.List;


/**
 * Service interface for {@link AudienceDTO}
 */
public interface IDilaAudienceService
{
    /**
     * Method to find all audiences
     * @return the list of all {@link AudienceDTO}
     */
    List<AudienceDTO> findAll( );
}
