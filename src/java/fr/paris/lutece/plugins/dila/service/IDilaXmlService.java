package fr.paris.lutece.plugins.dila.service;

import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;

import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;


/**
 * Interface for {@link XmlDTO}
 */
public interface IDilaXmlService
{
    /**
     * Find the title of a {@link XmlDTO}
     * @param id the id to find
     * @return the corresponding title
     */
    String findTitleById( String id );

    /**
     * Find a {@link XmlDTO} by its id
     * @param id the id to find
     * @return the corresponding {@link XmlDTO}
     */
    XmlDTO findFolderById( String id );

    /**
     * Find the resource type of a {@link XmlDTO}
     * @param strIdXml the xml id
     * @param lIdAudience the audience id
     * @return the corresponding type_resource
     */
    String findResourceTypeByIdXMLAndAudience( String strIdXml, Long lIdAudience );

    /**
     * Find a {@link XmlDTO} by its id and type
     * @param idThemeParent the id to find
     * @param availableTypes list of types for the resource
     * @param idAudience the id audience
     * @return the corresponding title
     */
    XmlDTO findByIdAndTypesAndAudience( String idThemeParent, List<String> availableTypes, Long idAudience );

    /**
     * Find the title of a {@link XmlDTO}
     * @param siblingFolderId the id to find
     * @param availableTypes list of types for the resource
     * @param idAudience the id audience
     * @return the corresponding title
     */
    String findTitleByIdAndTypesAndAudience( String siblingFolderId, List<String> availableTypes, Long idAudience );

    /**
     * Find all XML
     * @return list of all XML files
     */
    List<XmlDTO> findAll( );

    /**
     * Insert "comment faire" resource in document
     * @param audienceId the audience to check
     * @param builder the document builder
     * @param document the document
     * @return the updated document
     */
    Document insertHowToLinks( Long audienceId, DocumentBuilder builder, Document document );

    /**
     * Find a national card's technical id by it xml name and audience
     * @param xmlName the xmlName
     * @param audienceId the audience id
     * @return the technical id, null if not found
     */
    Long findIdByXmlAndAudience( String xmlName, Long audienceId );

    /**
     * Access RSS feeds and get links to display in document
     * @param builder the builder
     * @param document the document
     * @param locale the locale
     * @return the modified document
     */
    Document insertRssLinks( DocumentBuilder builder, Document document, Locale locale );

    /**
     * Find the "How to ..." section for given audience
     * @param audienceId the audienceId
     * @return {@link XmlDTO} for "How to ..." section of given audience
     */
    XmlDTO findHomeHowTo( Long audienceId );
}
