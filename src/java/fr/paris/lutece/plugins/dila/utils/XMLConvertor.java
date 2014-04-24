/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dila.utils;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;
import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


/**
 * XML convertor utility class
 */
public final class XMLConvertor
{
    // XML Properties
    private static final String LINE_DATE = "<dc:date>modified <%DATE%></dc:date>";
    private static final String LINE_FORMAT = "<dc:format>text/xml</dc:format>";
    private static final String LINE_ID = "<dc:identifier><%ID%></dc:identifier>";
    private static final String LINE_AUDIENCE = "<Audience top=\"false\"><%AUDIENCE%></Audience>";
    private static final String LINE_BEGIN_FIL_ARIANE = "<FilDAriane>";
    private static final String LINE_END_FIL_ARIANE = "</FilDAriane>";
    private static final String LINE_BEGIN_NIVEAU = "<Niveau ID=\"<%NIVEAU_ID%>\">";
    private static final String LINE_END_NIVEAU = "</Niveau>";
    private static final String LINE_BEGIN_THEME = "<Theme ID=\"<%THEME_ID%>\">";
    private static final String LINE_END_THEME = "</Theme>";
    private static final String CARD_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><?xml-stylesheet href=\"../StyleSheets/VDD11.xsl\" type=\"text/xsl\"?>";
    private static final String CARD_LINE_PUBLICATION = "<Publication xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"../Schemas/2.2/Publication.xsd\" type=\"Fiche d'information\" ID=\"<%ID%>\">";
    private static final String CARD_LINE_END_PUBLICATION = "</Publication>";
    private static final String CARD_LINE_TITLE = "<dc:title><%CARD_TITLE%></dc:title>";
    private static final String CARD_LINE_AUTEUR = "<dc:creator><%CARD_AUTEUR%></dc:creator>";
    private static final String CARD_LINE_TYPE = "<dc:type>Fiche</dc:type>";
    private static final String CARD_LINE_BEGIN_FOLDER_PERE = "<DossierPere ID=\"<%CARD_PERE_ID%>\">";
    private static final String CARD_LINE_END_FOLDER_PERE = "</DossierPere>";
    private static final String CARD_LINE_BEGIN_TEXTE = "<Texte>";
    private static final String CARD_LINE_END_TEXTE = "</Texte>";
    private static final String CARD_LINE_BEGIN_CHAPITRE = "<Chapitre>";
    private static final String CARD_LINE_END_CHAPITRE = "</Chapitre>";
    private static final String CARD_LINE_BEGIN_TITRE = "<Titre>";
    private static final String CARD_LINE_END_TITRE = "</Titre>";
    private static final String CARD_LINE_BEGIN_PARAGRAPHE = "<Paragraphe>";
    private static final String CARD_LINE_END_PARAGRAPHE = "</Paragraphe>";
    private static final String FOLDER_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><?xml-stylesheet href=\"../StyleSheets/VDD09.xsl\" type=\"text/xsl\"?>";
    private static final String FOLDER_LINE_PUBLICATION = "<Publication xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"../Schemas/2.2/Publication.xsd\" type=\"Dossier\" ID=\"<%ID%>\">";
    private static final String FOLDER_LINE_END_PUBLICATION = "</Publication>";
    private static final String FOLDER_LINE_TITLE = "<dc:title><%FOLDER_TITLE%></dc:title>";
    private static final String FOLDER_LINE_AUTEUR = "<dc:creator><%FOLDER_AUTEUR%></dc:creator>";
    private static final String FOLDER_LINE_TYPE = "<dc:type>Dossier</dc:type>";
    private static final String FOLDER_LINE_BEGIN_THEME_PERE = "<Theme ID=\"<%THEME_PERE_ID%>\">";
    private static final String FOLDER_LINE_END_THEME_PERE = "</Theme>";
    private static final String FOLDER_LINE_BEGIN_PRESENTATION = "<Presentation>";
    private static final String FOLDER_LINE_END_PRESENTATION = "</Presentation>";
    private static final String FOLDER_LINE_BEGIN_CARD = "<Fiche seq=\"<%SEQ_ID%>\" ID=\"<%CARD_ID%>\">";
    private static final String FOLDER_LINE_END_CARD = "</Fiche>";

    // Service
    private static final IDilaLocalService _dilaLocalService = SpringContextService.getBean( "dilaLocalService" );
    private static final IDilaXmlService _dilaXmlService = SpringContextService.getBean( "dilaXmlService" );

    /**
     * Private constructor
     */
    private XMLConvertor( )
    {
    }

    /**
     * Convert a {@link LocalCardDTO} into xml string
     * @param card the card to convert
     * @param chapters list of {@link LocalCardChapterDTO} linked to the
     *            {@link LocalCardDTO}
     * @return the formatted XML
     */
    public static String convertCardToXML( LocalCardDTO card, List<LocalCardChapterDTO> chapters )
    {
        SimpleDateFormat formatDate = new SimpleDateFormat( "yyyy-MM-dd" );
        String date = formatDate.format( new Date( ) );

        StringBuilder result = new StringBuilder( );

        result.append( CARD_HEADER );
        result.append( CARD_LINE_PUBLICATION );

        // Construct header
        result.append( CARD_LINE_TITLE.replace( "<%CARD_TITLE%>", card.getLocalDTO( ).getTitle( ) ) );
        result.append( CARD_LINE_AUTEUR.replace( "<%CARD_AUTEUR%>", card.getLocalDTO( ).getAuthor( ) ) );
        result.append( LINE_DATE.replace( "<%DATE%>", date ) );
        result.append( CARD_LINE_TYPE );
        result.append( LINE_FORMAT );
        result.append( LINE_ID );
        result.append( LINE_AUDIENCE.replace( "<%AUDIENCE%>",
                AudienceCategoryEnum.fromId( card.getLocalDTO( ).getIdAudience( ) ).getLabel( ) ) );

        // Start construct the breadcrumb
        result.append( LINE_BEGIN_FIL_ARIANE );

        String strBreadcrumb = "";

        strBreadcrumb = card.getLocalDTO( ).getBreadCrumb( );

        String[] breadcrumbs = strBreadcrumb.split( ";" );

        String theme = null;

        for ( String breadcrumb : breadcrumbs )
        {
            if ( StringUtils.isNotBlank( breadcrumb ) )
            {
                if ( theme == null )
                {
                    theme = breadcrumb;
                }

                result.append( LINE_BEGIN_NIVEAU.replace( "<%NIVEAU_ID%>", breadcrumb ) );

                if ( StringUtils.isNumeric( breadcrumb ) )
                {
                    result.append( _dilaLocalService.findTitleByIdAndTypeAndAudience( Long.valueOf( breadcrumb ),
                            DilaLocalTypeEnum.FOLDER.getId( ), card.getLocalDTO( ).getIdAudience( ) ) );
                }
                else
                {
                    result.append( _dilaXmlService.findTitleById( breadcrumb ) );
                }

                result.append( LINE_END_NIVEAU );
            }
        }

        result.append( LINE_END_FIL_ARIANE );

        // Start construct theme
        if ( theme != null )
        {
            result.append( LINE_BEGIN_THEME.replace( "<%THEME_ID%>", theme ) );
            result.append( _dilaXmlService.findTitleById( theme ) );
            result.append( LINE_END_THEME );
        }

        // Start construct the parent folder
        result.append( CARD_LINE_BEGIN_FOLDER_PERE.replace( "<%CARD_PERE_ID%>", card.getParentFolderId( ) ) );
        result.append( CARD_LINE_BEGIN_TITRE );

        if ( StringUtils.isNumeric( card.getParentFolderId( ) ) )
        {
            result.append( card.getLocalParentFolder( ).getLocalDTO( ).getTitle( ) );
        }
        else
        {
            result.append( card.getNationalParentFolder( ).getTitle( ) );
        }

        result.append( CARD_LINE_END_TITRE );
        result.append( CARD_LINE_END_FOLDER_PERE );

        // Start construct the chapters
        result.append( CARD_LINE_BEGIN_TEXTE );

        for ( LocalCardChapterDTO chapter : chapters )
        {
            result.append( CARD_LINE_BEGIN_CHAPITRE );

            // set title
            result.append( CARD_LINE_BEGIN_TITRE );
            result.append( CARD_LINE_BEGIN_PARAGRAPHE );
            result.append( chapter.getTitle( ) );
            result.append( CARD_LINE_END_PARAGRAPHE );
            result.append( CARD_LINE_END_TITRE );

            // set content
            result.append( CARD_LINE_BEGIN_PARAGRAPHE );
            result.append( StringEscapeUtils.unescapeHtml( chapter.getContent( ) ) );
            result.append( CARD_LINE_END_PARAGRAPHE );

            result.append( CARD_LINE_END_CHAPITRE );
        }

        result.append( CARD_LINE_END_TEXTE );

        result.append( CARD_LINE_END_PUBLICATION );

        return result.toString( );
    }

    /**
     * Convert a {@link LocalFolderDTO} into xml string
     * @param folder the folder to convert
     * @param links list of {@link LocalFolderLinkDTO} linked to the
     *            {@link LocalFolderDTO}
     * @return the formatted XML
     */
    public static String convertFolderInXML( LocalFolderDTO folder, List<LocalFolderLinkDTO> links )
    {
        SimpleDateFormat formatDate = new SimpleDateFormat( "yyyy-MM-dd" );
        String date = formatDate.format( new Date( ) );

        StringBuilder result = new StringBuilder( );

        result.append( FOLDER_HEADER );
        result.append( FOLDER_LINE_PUBLICATION );

        // Construct header
        result.append( FOLDER_LINE_TITLE.replace( "<%FOLDER_TITLE%>", folder.getLocalDTO( ).getTitle( ) ) );
        result.append( FOLDER_LINE_AUTEUR.replace( "<%FOLDER_AUTEUR%>", folder.getLocalDTO( ).getAuthor( ) ) );
        result.append( LINE_DATE.replace( "<%DATE%>", date ) );
        result.append( FOLDER_LINE_TYPE );
        result.append( LINE_FORMAT );
        result.append( LINE_ID );
        result.append( LINE_AUDIENCE.replace( "<%AUDIENCE%>",
                AudienceCategoryEnum.fromId( folder.getLocalDTO( ).getIdAudience( ) ).getLabel( ) ) );

        // Start construct the breadcrumb
        result.append( LINE_BEGIN_FIL_ARIANE );

        String strBreadcrumb = folder.getLocalDTO( ).getBreadCrumb( );
        String theme = null;

        if ( StringUtils.isNotBlank( strBreadcrumb ) )
        {
            String[] breadcrumbs = strBreadcrumb.split( ";" );

            for ( String breadcrumb : breadcrumbs )
            {
                //avoid empty breadcrumb
                if ( StringUtils.isNotBlank( breadcrumb ) )
                {

                    if ( theme == null )
                    {
                        theme = breadcrumb;
                    }

                    result.append( LINE_BEGIN_NIVEAU.replace( "<%NIVEAU_ID%>", breadcrumb ) );

                    if ( StringUtils.isNumeric( breadcrumb ) )
                    {
                        result.append( _dilaLocalService.findTitleByIdAndTypeAndAudience( Long.valueOf( breadcrumb ),
                                DilaLocalTypeEnum.FOLDER.getId( ), folder.getLocalDTO( ).getIdAudience( ) ) );
                    }
                    else
                    {
                        result.append( _dilaXmlService.findTitleById( breadcrumb ) );
                    }
                    result.append( LINE_END_NIVEAU );
                }

            }
        }

        result.append( LINE_END_FIL_ARIANE );

        // Start construct theme
        if ( theme != null )
        {
            result.append( LINE_BEGIN_THEME.replace( "<%THEME_ID%>", theme ) );
            result.append( _dilaXmlService.findTitleById( theme ) );
            result.append( LINE_END_THEME );
        }

        // Start construct the parent theme
        result.append( FOLDER_LINE_BEGIN_THEME_PERE.replace( "<%THEME_PERE_ID%>", folder.getParentThemeId( ) ) );
        result.append( _dilaXmlService.findTitleById( folder.getParentThemeId( ) ) );
        result.append( FOLDER_LINE_END_THEME_PERE );

        result.append( FOLDER_LINE_BEGIN_PRESENTATION );
        result.append( folder.getPresentation( ) );
        result.append( FOLDER_LINE_END_PRESENTATION );

        // construct links
        if ( links.size( ) > 0 )
        {
            for ( LocalFolderLinkDTO link : links )
            {
                result.append( FOLDER_LINE_BEGIN_CARD.replace( "<%SEQ_ID%>", "" + link.getPosition( ) ).replace(
                        "<%CARD_ID%>", link.getCardId( ) ) );
                result.append( link.getTitle( ) );
                result.append( FOLDER_LINE_END_CARD );
            }
        }

        result.append( FOLDER_LINE_END_PUBLICATION );

        return result.toString( );
    }
}
