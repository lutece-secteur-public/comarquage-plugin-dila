package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;

import java.util.List;


/**
 * DAO for {@link ComplementaryDataLinkDTO}
 * @author rputegnat
 */
public interface IComplementaryDataLinkDAO
{
    /**
     * Create a {@link ComplementaryDataLinkDTO}
     * @param dto the {@link ComplementaryDataLinkDTO} to create
     */
    void insert( ComplementaryDataLinkDTO dto );

    /**
     * Delete all links of a complementary data
     * @param id the donnee complementaire id to find
     * @param type the type of dto to delete
     */
    void delete( Long id, ComplementaryLinkTypeEnum type );

    /**
     * Find links by data id and link type
     * @param dataId the id of complementary data
     * @param type the type of dto
     * @return list of corresponding {@link ComplementaryDataLinkDTO}
     */
    List<ComplementaryDataLinkDTO> findByDataId( Long dataId, ComplementaryLinkTypeEnum type );

}
