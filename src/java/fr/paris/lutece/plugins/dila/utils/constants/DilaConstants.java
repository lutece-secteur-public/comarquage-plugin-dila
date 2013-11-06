/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.dila.utils.constants;

/**
 * 
 * DilaConstants
 * 
 */
public final class DilaConstants
{
    // MARKERS
    public static final String MARK_NAME = "name";
    public static final String MARK_CONTENT_TYPE_ID = "type_contenu_id";
    public static final String MARK_CONTENT_TYPE_LIST = "type_contenu_list";
    public static final String MARK_SOURCE = "source";
    public static final String MARK_STYLESHEET_LIST = "stylesheet_list";
    public static final String MARK_PAGINATOR = "paginator";
    public static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    public static final String MARK_STYLESHEET = "stylesheet";
    public static final String MARK_STYLESHEET_ID = "stylesheet_id";
    public static final String MARK_CARD_ID = "id_fiche_locale";
    public static final String MARK_LOCAL_ID = "id_local";
    public static final String MARK_ID = "id";
    public static final String MARK_PERMISSION_CREATE_STYLESHEET = "permission_create_stylesheet";
    public static final String MARK_PERMISSION_DELETE_STYLESHEET = "permission_delete_stylesheet";
    public static final String MARK_PERMISSION_MODIFY_STYLESHEET = "permission_modify_stylesheet";
    public static final String MARK_PERMISSION_VIEW_STYLESHEET = "permission_view_stylesheet";
    public static final String MARK_COMP_DATA_LIST = "donnees_list";
    public static final String MARK_XMLURL_PARAM = "XMLURL";
    public static final String MARK_CATEGORY_PARAM = "CATEGORIE";
    public static final String MARK_HOW_TO_ID_PARAM = "HOW_TO_ID";
    public static final String MARK_HOW_TO_TITLE_PARAM = "HOW_TO_TITLE";
    public static final String MARK_REFERER_PARAM = "REFERER";
    public static final String MARK_CARDID_PARAM = "FICHEID";

    // Properties
    public static final String LABEL_ALL = "dila.manage_style_sheet.label_all";
    public static final String LABEL_WITHOUT_TYPE = "dila.create_style_sheet.label_without_type";
    public static final String PROPERTY_PATH_XSL = "path.stylesheet";
    public static final String PROPERTY_STYLESHEETS_PER_PAGE = "paginator.stylesheet.itemsPerPage";
    public static final String PROPERTY_TMP_DIRECTORY = "archives.dir.path.tmp";
    public static final String PROPERTY_XML_DIRECTORY = "archive.dir.path.extract.final";
    public static final String PROPERTY_XML_DIRECTORY_ASSO = "association.archive.dir.path.extract.final";
    public static final String PROPERTY_XML_DIRECTORY_INDIVIDUALS = "individual.archive.dir.path.extract.final";
    public static final String PROPERTY_XML_DIRECTORY_PROFESSIONALS = "professional.archive.dir.path.extract.final";
    public static final String PROPERTY_XML_DIRECTORY_LOCALES_TOWN = "xmlDirectory.locales.communes";
    public static final String PROPERTY_XML_DIRECTORY_LOCALES_ORGANISMS = "xmlDirectory.locales.organismes";
    public static final String PROPERTY_INSEE = "dila.insee";
    public static final String PROPERTY_XML_URL_INDIVIDUALS = "individual.archive.url";
    public static final String PROPERTY_XML_NAME_INDIVIDUALS = "individual.archive.name";
    public static final String PROPERTY_XML_TMP_INDIVIDUALS = "individual.archive.dir.path.extract.tmp";
    public static final String PROPERTY_XML_FINAL_INDIVIDUALS = "individual.archive.dir.path.extract.final";
    public static final String PROPERTY_XML_URL_ASSO = "association.archive.url";
    public static final String PROPERTY_XML_NAME_ASSO = "association.archive.name";
    public static final String PROPERTY_XML_TMP_ASSO = "association.archive.dir.path.extract.tmp";
    public static final String PROPERTY_XML_FINAL_ASSO = "association.archive.dir.path.extract.final";
    public static final String PROPERTY_XML_URL_PME = "professional.archive.url";
    public static final String PROPERTY_XML_NAME_PME = "professional.archive.name";
    public static final String PROPERTY_XML_TMP_PME = "professional.archive.dir.path.extract.tmp";
    public static final String PROPERTY_XML_FINAL_PME = "professional.archive.dir.path.extract.final";
    public static final String PROPERTY_XML_URL_LOCALES = "local.data.archive.url";
    public static final String PROPERTY_XML_NAME_LOCALES = "local.data.archive.name";
    public static final String PROPERTY_XML_TMP_LOCALES = "local.data.archive.dir.path.extract.tmp";
    public static final String PROPERTY_XML_FINAL_LOCALES = "local.data.archive.dir.path.extract.final";
    public static final String PROCESS_INDIVIDUAL = "individual";
    public static final String PROCESS_ASSOCIATION = "association";
    public static final String PROCESS_PROFESSIONAL = "professional";
    public static final String PROCESS_LOCAL_DATA = "local data";
    public static final String EXTENSION_ZIP = ".zip";
    public static final String EXTENSION_BZIP2 = ".bz2";
    public static final String EXTENSION_XML = ".xml";

    // Messages
    public static final String MESSAGE_MANDATORY_FIELD = "dila.message.mandatory_field";
    public static final String MESSAGE_STYLESHEET_ALREADY_EXISTS = "dila.message.stylesheetAlreadyExists";
    public static final String MESSAGE_STYLESHEET_NOT_VALID = "dila.message.stylesheetNotValid";
    public static final String MESSAGE_STYLESHEET_NAME_EXISTS = "dila.message.stylesheetNameExists";
    public static final String MESSAGE_ERROR_OCCUR = "dila.message.error.erroroccur";
    public static final String MESSAGE_TITLE_DELETE_STYLESHEET = "dila.message.title.delete_stylesheet";
    public static final String MESSAGE_CONFIRMATION_DELETE_STYLESHEET = "dila.message.confirmation.delete_stylesheet";
    public static final String MESSAGE_CONFIRMATION_DELETE_FICHE = "dila.message.confirmation.delete_fichelocale";
    public static final String MESSAGE_TITLE_DELETE_FICHE = "dila.message.title.delete_fichelocale";
    public static final String MESSAGE_CONFIRMATION_DELETE_DOSSIER = "dila.message.confirmation.delete_dossierlocal";
    public static final String MESSAGE_TITLE_DELETE_DOSSIER = "dila.message.title.delete_dossierlocal";
    public static final String MESSAGE_CONFIRMATION_DELETE_DONNEE = "dila.message.confirmation.delete_donneecomplementaire";
    public static final String MESSAGE_TITLE_DELETE_DONNEE = "dila.message.title.delete_donneecomplementaire";

    // Templates
    public static final String TEMPLATE_MANAGE_STYLESHEETS = "admin/plugins/dila/manage_stylesheets.html";
    public static final String TEMPLATE_CREATE_STYLESHEET = "admin/plugins/dila/save_stylesheet.html";

    // Jsp
    public static final String JSP_MANAGE_STYLESHEET = "jsp/admin/plugins/dila/ManageStyleSheets.jsp";
    public static final String JSP_MANAGE_LOCAL = "jsp/admin/plugins/dila/ManageLocalCard.jsp";
    public static final String JSP_DELETE_DOSSIER = "jsp/admin/plugins/dila/DoDeleteLocalFolder.jsp";
    public static final String JSP_DELETE_STYLESHEET = "jsp/admin/plugins/dila/DoDeleteStyleSheet.jsp";
    public static final String JSP_DELETE_FICHE = "jsp/admin/plugins/dila/DoDeleteLocalCard.jsp";
    public static final String JSP_DELETE_DONNEE = "jsp/admin/plugins/dila/DoDeleteComplementaryData.jsp";
    public static final String JSP_MANAGE_DONNEES = "jsp/admin/plugins/dila/ManageComplementaryData.jsp";
    public static final String CACHE_KEY_SEPARATOR = "_";
    public static final String DEFAULT_XML = "Themes";
    public static final String XML_EXTENSION = ".xml";
    public static final String XPAGE_REFERER = "jsp/site/Portal.jsp?page=dila&categorie=";
    public static final String XPAGE_REFERER_XMLFILE = "&xmlFile=";
    public static final String XPAGE_PARTICULIERS = "jsp/site/Portal.jsp?page=dila&categorie=particuliers";
    public static final String XPAGE_PME = "jsp/site/Portal.jsp?page=dila&categorie=professionnels";
    public static final String XPAGE_ASSO = "jsp/site/Portal.jsp?page=dila&categorie=associations";

    /**
     * Utility class
     */
    private DilaConstants( )
    {
        // nothing
    }
}
