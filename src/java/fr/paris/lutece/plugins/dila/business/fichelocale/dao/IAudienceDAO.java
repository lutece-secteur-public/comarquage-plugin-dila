package fr.paris.lutece.plugins.dila.business.fichelocale.dao;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;

import java.util.List;


/**
 * DAO for {@link AudienceDTO}
 */
public interface IAudienceDAO
{
    /**
     * Method to find all audiences
     * @return the list of all {@link AudienceDTO}
     */
    List<AudienceDTO> findAll( );
}
