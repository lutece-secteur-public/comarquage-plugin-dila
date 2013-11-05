package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.exception.DilaException;
import fr.paris.lutece.plugins.dila.service.IDilaBatchXMLService;
import fr.paris.lutece.plugins.dila.service.IDilaIndexationService;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.plugins.dila.utils.filter.FilenameFilterXml;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.jdom2.JDOMException;


/**
 * Implementation of {@link IDilaIndexationService}
 */
public class DilaIndexationService implements IDilaIndexationService
{
    @Inject
    @Named( "dilaBatchXmlService" )
    private IDilaBatchXMLService _dilaBatchXMLService;

    @Override
    public void indexAll( ) throws DilaException
    {
        AppLogService.info( "Begin index" );

        // Individual
        process( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_INDIVIDUALS ),
                DilaConstants.PROCESS_INDIVIDUAL, AudienceCategoryEnum.INDIVIDUALS );

        // Association
        process( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_ASSO ),
                DilaConstants.PROCESS_ASSOCIATION, AudienceCategoryEnum.ASSOCIATIONS );

        // Professional
        process( AppPropertiesService.getProperty( DilaConstants.PROPERTY_XML_FINAL_PME ),
                DilaConstants.PROCESS_PROFESSIONAL, AudienceCategoryEnum.PROFESSIONNALS );

        _dilaBatchXMLService.delete( );
    }

    /**
     * Process XML files
     * @param strDirPath directory path to XML files
     * @param strProcessName process name
     * @param typeXml the type XML
     * @throws IOException
     * @throws JDOMException
     * @throws DilaException occurs during treatment
     */
    private void process( String strDirPath, String strProcessName, AudienceCategoryEnum typeXml ) throws DilaException
    {
        File dirXML = new File( strDirPath );
        if ( dirXML.exists( ) )
        {
            File[] listXML = dirXML.listFiles( new FilenameFilterXml( ) );
            if ( listXML != null )
            {
                AppLogService.info( "Processing " + strProcessName + " XML files available in directory " + strDirPath );

                for ( File file : listXML )
                {
                    try
                    {
                        _dilaBatchXMLService.processXMLFile( file, typeXml );
                    }
                    catch ( JDOMException jdom )
                    {
                        throw new DilaException( "Error during file " + file.getName( ) + " parsing." );
                    }
                    catch ( IOException ioe )
                    {
                        throw new DilaException( "Error during file " + file.getName( ) + " management." );
                    }
                }
            }
            else
            {
                AppLogService.info( "No " + strProcessName + " XML files in directory " + strDirPath );
            }
        }
    }

}
