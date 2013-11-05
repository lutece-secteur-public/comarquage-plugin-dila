package fr.paris.lutece.plugins.dila.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * This interface provides a method to find XML filenames according to code
 * pivot
 * 
 */
public interface IDilaPivotLocalService
{
    /**
     * Find all XML filenames according to strCodePivot and insee number
     * @param strCodePivot the code pivot
     * @return list of corresponding filenames
     */
    List<String> findByCodePivot( String strCodePivot );

    /**
     * Insert pivots data into xml
     * @param builder the builder to use
     * @param document the document to complete
     * @throws SAXException occurs during XML transform
     * @throws IOException occurs during XML read
     * @return the document with pivots
     */
    Document insertPivots( DocumentBuilder builder, Document document ) throws SAXException, IOException;

}
