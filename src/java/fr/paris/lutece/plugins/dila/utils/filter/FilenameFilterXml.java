package fr.paris.lutece.plugins.dila.utils.filter;

import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;


/**
 * Filter to accept only XML
 * 
 */
public class FilenameFilterXml implements FilenameFilter
{

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept( File dir, String name )
    {
        boolean bAccept = false;

        if ( StringUtils.isNotBlank( name ) )
        {
            String strFilename = name.toLowerCase( );
            if ( strFilename.endsWith( DilaConstants.EXTENSION_XML ) )
            {
                bAccept = true;
            }
        }

        return bAccept;
    }

}
