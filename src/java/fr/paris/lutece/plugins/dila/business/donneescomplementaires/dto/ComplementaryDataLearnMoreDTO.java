package fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto;

import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;


/**
 * DTO for link
 */
public class ComplementaryDataLearnMoreDTO extends ComplementaryDataLinkDTO
{

    @Override
    public ComplementaryLinkTypeEnum getType( )
    {
        return ComplementaryLinkTypeEnum.LEARN_MORE;
    }
}
