package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto;

import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;


/**
 * DTO for teleservice
 */
public class ComplementaryDataTeleserviceDTO extends ComplementaryDataLinkDTO
{

    @Override
    public ComplementaryLinkTypeEnum getType( )
    {
        return ComplementaryLinkTypeEnum.TELESERVICE;
    }
}
