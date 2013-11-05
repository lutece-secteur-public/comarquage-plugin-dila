package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;

import java.io.File;
import java.io.IOException;

import org.jdom2.JDOMException;


/**
 * Interface for batch xml
 */
public interface IDilaBatchXMLService
{
    /**
     * Index one file
     * @param file file to index
     * @param typeXML type of file
     * @throws JDOMException occurs during file parsing
     * @throws IOException occurs during treatment
     */
    void processXMLFile( File file, AudienceCategoryEnum typeXML ) throws JDOMException, IOException;

    /**
     * Transform a document in {@link XmlDTO}
     * @param file source file
     * @param typeXML type file
     * @return the build xml
     * @throws JDOMException occurs during file parsing
     * @throws IOException occurs during treatment
     */
    XmlDTO buildDocument( File file, AudienceCategoryEnum typeXML ) throws JDOMException, IOException;

    /**
     * Delete outdated files
     */
    void delete( );
}
