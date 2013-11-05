package fr.paris.lutece.plugins.dila.utils;

import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import org.apache.commons.lang.StringUtils;


/**
 * Dila Utils class
 */
public final class DilaUtils
{
    private static final IDilaLocalService _dilaLocalService = SpringContextService.getBean( "dilaLocalService" );
    private static final IDilaXmlService _dilaXmlService = SpringContextService.getBean( "dilaXmlService" );

    /**
     * Private constructor
     */
    private DilaUtils( )
    {

    }

    /**
     * Convert the breadcrumb into a displayable format
     * @param originalBreadcrumb original breadcrumb
     * @param idAudience the id audience
     * @return the formatted breadcrumb
     */
    public static String convertBreadcrumbIntoDisplay( String originalBreadcrumb, Long idAudience )
    {
        StringBuilder displayBreadcrumb = new StringBuilder( "" );

        String[] breadcrumbs = originalBreadcrumb.split( ";" );

        for ( String breadcrumb : breadcrumbs )
        {
            if ( StringUtils.isNotBlank( breadcrumb ) )
            {
                if ( StringUtils.isNumeric( breadcrumb ) )
                {
                    displayBreadcrumb.append( _dilaLocalService.findTitleByIdAndTypeAndAudience(
                            Long.valueOf( breadcrumb ), DilaLocalTypeEnum.FOLDER.getId( ), idAudience ) );
                }
                else
                {
                    displayBreadcrumb.append( _dilaXmlService.findTitleById( breadcrumb ) );
                }

                displayBreadcrumb.append( " > " );
            }
        }

        return displayBreadcrumb.substring( 0, displayBreadcrumb.length( ) - 2 );
    }
}
