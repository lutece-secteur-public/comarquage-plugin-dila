package fr.paris.lutece.plugins.dila.utils.filter;

import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;


/**
 * Filter to accept only archives
 */
public class FilenameFilterArchive implements FilenameFilter
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
            if ( strFilename.endsWith( DilaConstants.EXTENSION_ZIP )
                    || strFilename.endsWith( DilaConstants.EXTENSION_BZIP2 ) )
            {
                bAccept = true;
            }
        }

        return bAccept;
    }

}
