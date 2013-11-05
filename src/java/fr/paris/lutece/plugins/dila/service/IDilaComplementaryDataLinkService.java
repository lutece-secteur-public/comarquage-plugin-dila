package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;

import java.util.List;


/**
 * Service for {@link ComplementaryDataLinkDTO}
 */
public interface IDilaComplementaryDataLinkService
{
    /**
     * Create a {@link ComplementaryDataLinkDTO}
     * @param dto the {@link ComplementaryDataLinkDTO} to create
     */
    void create( ComplementaryDataLinkDTO dto );

    /**
     * Delete all teleservice linked to a complementary data
     * @param lId the complementary data id to find
     * @param type the type of dto
     */
    void deleteFromComplementaryData( Long lId, ComplementaryLinkTypeEnum type );

    /**
     * Find links by complementary data id and type
     * @param lComplementaryDataId the id of complementary data
     * @param type the type of dto
     * @return list of corresponding {@link ComplementaryDataLinkDTO}
     */
    List<ComplementaryDataLinkDTO> findByDataId( Long lComplementaryDataId, ComplementaryLinkTypeEnum type );
}
