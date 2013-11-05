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
package fr.paris.lutece.plugins.dila.web;

import fr.paris.lutece.plugins.dila.business.enums.ActionTypeEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardChapterDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalFolderLinkDTO;
import fr.paris.lutece.plugins.dila.service.IDilaAudienceService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalCardChapterService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalCardService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalFolderLinkService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalFolderService;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.service.IDilaValidationService;
import fr.paris.lutece.plugins.dila.utils.ListUtils;
import fr.paris.lutece.plugins.dila.utils.XMLConvertor;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.pluginaction.DefaultPluginActionResult;
import fr.paris.lutece.portal.web.pluginaction.IPluginActionResult;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.datatable.DataTableManager;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


/**
 * DilaJspBean
 */
public class LocalCardJspBean extends PluginAdminPageJspBean
{
    //Rights
    public static final String RIGHT_MANAGE_LOCAL_CARD = "DILA_LOCAL_CARD_MANAGEMENT";

    /** Serial id */
    private static final long serialVersionUID = -7968314250934413466L;

    //templates
    private static final String TEMPLATE_MANAGE_LOCAL = "admin/plugins/dila/manage_fiche-locale.html";
    private static final String TEMPLATE_CREATE_LOCAL_CARD = "admin/plugins/dila/create_fiche-locale.html";
    private static final String TEMPLATE_MODIFY_LOCAL_CARD = "admin/plugins/dila/modify_fiche-locale.html";
    private static final String TEMPLATE_CREATE_LOCAL_FOLDER = "admin/plugins/dila/create_dossier-local.html";
    private static final String TEMPLATE_MODIFY_LOCAL_FOLDER = "admin/plugins/dila/modify_dossier-local.html";

    //jsp
    private static final String JSP_MANAGE_LOCAL_NAME = "ManageFicheLocale.jsp";

    //macro column names
    private static final String MACRO_COLUMN_ACTIONS_FICHE_LOCALE = "columnActionsFicheLocale";

    // PARAMETERS
    private static final String PARAMETER_CHAPTER_ID = "chapterId";
    private static final String PARAMETER_CHAPTERS_LIST = "chapters_list";
    private static final String PARAMETER_LINK_ID = "linkId";
    private static final String PARAMETER_LINK_LIST = "links_list";
    private static final String PARAMETER_REFERENCE_ID = "referenceId";
    private static final String PARAMETER_TYPE = "type";
    private static final String PARAMETER_NOM = "nomFicheLocale";
    private static final String PARAMETER_AUTEUR = "auteurFicheLocale";

    // marks
    private static final String MARK_DATA_TABLE_FICHE_LOCALE = "dataTableFicheLocale";
    private static final String MARK_FICHE_LOCALE = "ficheLocale";
    private static final String MARK_DOSSIER_LOCAL = "dossierLocal";
    private static final String MARK_AUDIENCE = "audiences";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";

    // Beans
    private LocalCardDTO _cardBean;
    private LocalFolderDTO _folderBean;
    private String _strAction;
    private DataTableManager<LocalDTO> _dataTableLocale;

    // Services
    private IDilaLocalFolderService _dilaLocalFolderService = SpringContextService.getBean( "dilaLocalFolderService" );
    private IDilaLocalService _dilaLocalService = SpringContextService.getBean( "dilaLocalService" );
    private IDilaLocalCardService _dilaLocalCardService = SpringContextService.getBean( "dilaLocalCardService" );
    private IDilaAudienceService _dilaAudienceService = SpringContextService.getBean( "dilaAudienceService" );
    private IDilaLocalCardChapterService _dilaLocalCardChapterService = SpringContextService
            .getBean( "dilaLocalCardChapterService" );
    private IDilaLocalFolderLinkService _dilaLocalFolderLinkService = SpringContextService
            .getBean( "dilaLocalFolderLinkService" );
    private IDilaValidationService _dilaValidationService = SpringContextService.getBean( "dilaValidationService" );

    /**
     * Return management local cards and folders
     * @param request the request
     * @return the page
     */
    public String getManageFicheLocale( HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>( );

        String strType = request.getParameter( PARAMETER_TYPE );
        Long lType = 0L;
        if ( StringUtils.isNotBlank( strType ) )
        {
            lType = Long.valueOf( strType );
        }

        String strId = request.getParameter( DilaConstants.MARK_ID );
        Long lId = 0L;
        if ( StringUtils.isNotBlank( strId ) && StringUtils.isNumeric( strId ) )
        {
            lId = Long.valueOf( strId );
        }

        String strName = request.getParameter( PARAMETER_NOM );
        String strAuthor = request.getParameter( PARAMETER_AUTEUR );

        //Init fiche locale data table
        if ( _dataTableLocale == null )
        {
            _dataTableLocale = new DataTableManager<LocalDTO>( DilaConstants.JSP_MANAGE_LOCAL, "", 10, true );
            _dataTableLocale.addColumn( "dila.manage_local.row_id", "id", true );
            _dataTableLocale.addColumn( "dila.manage_local.row_type", "typeLabel", true );
            _dataTableLocale.addColumn( "dila.manage_local.row_title", "title", true );
            _dataTableLocale.addColumn( "dila.manage_local.row_breadcrumb", "displayPath", false );
            _dataTableLocale.addColumn( "dila.manage_local.row_author", "author", true );
            _dataTableLocale.addFreeColumn( "dila.manage_local.row_actions", MACRO_COLUMN_ACTIONS_FICHE_LOCALE );
        }

        List<LocalDTO> listLocalCards = _dilaLocalService.findAll( );

        _dataTableLocale.filterSortAndPaginate( request, filterLocal( listLocalCards, lType, lId, strName, strAuthor ) );
        model.put( MARK_DATA_TABLE_FICHE_LOCALE, _dataTableLocale );
        model.put( PARAMETER_TYPE, strType );
        model.put( DilaConstants.MARK_ID, strId );
        model.put( PARAMETER_NOM, strName );
        model.put( PARAMETER_AUTEUR, strAuthor );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_LOCAL, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Return create fiche locale
     * @param request The Http request
     * @return Html
     */
    public String getCreateFicheLocale( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.CREATE.getValue( );
        Map<String, Object> model = new HashMap<String, Object>( );

        _cardBean = new LocalCardDTO( );
        List<LocalCardChapterDTO> chapters = new ArrayList<LocalCardChapterDTO>( );

        LocalCardChapterDTO chapter = new LocalCardChapterDTO( );
        chapter.setPosition( 1 );

        chapters.add( chapter );

        model.put( MARK_FICHE_LOCALE, _cardBean );
        model.put( PARAMETER_CHAPTER_ID, 1 );
        model.put( PARAMETER_CHAPTERS_LIST, chapters );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_CREATE_LOCAL_CARD, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Return modify fiche locale
     * @param request The Http request
     * @return Html
     */
    public String getModifyFicheLocale( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.MODIFY.getValue( );
        String strLocalItemId = request.getParameter( DilaConstants.MARK_LOCAL_ID );

        if ( StringUtils.isEmpty( strLocalItemId ) || !StringUtils.isNumeric( strLocalItemId ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        _cardBean = _dilaLocalCardService.findCardByLocalId( Long.valueOf( strLocalItemId ) );

        if ( _cardBean == null )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        // get father directory infos
        _dilaValidationService.validateRootFolder( _cardBean, _cardBean.getParentFolderId( ) );

        Map<String, Object> model = new HashMap<String, Object>( );

        // find chapters by fiche id
        List<LocalCardChapterDTO> chapters = _dilaLocalCardChapterService.findByCardId( _cardBean.getId( ) );

        model.put( MARK_FICHE_LOCALE, _cardBean );
        model.put( PARAMETER_CHAPTER_ID, chapters.size( ) );
        model.put( PARAMETER_CHAPTERS_LIST, chapters );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MODIFY_LOCAL_CARD, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Return create dossier locale
     * @param request The Http request
     * @return Html
     */
    public String getCreateDossierLocal( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.CREATE.getValue( );
        Map<String, Object> model = new HashMap<String, Object>( );

        _folderBean = new LocalFolderDTO( );

        List<LocalFolderLinkDTO> links = new ArrayList<LocalFolderLinkDTO>( );

        model.put( PARAMETER_LINK_ID, 0 );
        model.put( PARAMETER_LINK_LIST, links );
        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_CREATE_LOCAL_FOLDER, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Return create dossier locale
     * @param request The Http request
     * @return Html
     */
    public String getModifyDossierLocal( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.MODIFY.getValue( );
        String idLocal = request.getParameter( DilaConstants.MARK_LOCAL_ID );

        Map<String, Object> model = new HashMap<String, Object>( );

        // find dossier by local id
        _folderBean = _dilaLocalFolderService.findFolderByLocalId( Long.valueOf( idLocal ) );

        // get theme parent informations
        _dilaValidationService.validateRootTheme( _folderBean );

        // get brother directory informations
        _dilaValidationService.validateLinkedFolder( _folderBean );

        // find links by dossier id
        List<LocalFolderLinkDTO> links = _dilaLocalFolderLinkService.findByFolderId( _folderBean.getId( ) );
        if ( CollectionUtils.isNotEmpty( links ) )
        {
            for ( LocalFolderLinkDTO link : links )
            {
                _dilaValidationService.validateLinkCard( _folderBean, link.getCardId( ), link );
            }
        }

        model.put( PARAMETER_LINK_ID, links.size( ) );
        model.put( PARAMETER_LINK_LIST, links );
        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MODIFY_LOCAL_FOLDER, getLocale( ), model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Add a chapter
     * @param request the page request
     * @return the ManageFicheLocale page
     */
    public String doAddChapter( HttpServletRequest request )
    {
        Integer nbChapter = Integer.valueOf( request.getParameter( PARAMETER_CHAPTER_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _cardBean, request );

        List<LocalCardChapterDTO> chapters = generateChapters( model, request );

        LocalCardChapterDTO chapter = new LocalCardChapterDTO( );
        chapter.setPosition( nbChapter + 1 );
        chapters.add( chapter );

        model.put( MARK_FICHE_LOCALE, _cardBean );
        model.put( PARAMETER_CHAPTER_ID, nbChapter + 1 );
        model.put( PARAMETER_CHAPTERS_LIST, chapters );
        setDataInModel( request, model );

        return getAdminPage( getTemplateFiche( model ) );
    }

    /**
     * Delete a chapter
     * @param request the page request
     * @return the ManageFicheLocale page
     */
    public String doDeleteChapter( HttpServletRequest request )
    {
        Integer nbChapter = Integer.valueOf( request.getParameter( PARAMETER_CHAPTER_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _cardBean, request );

        List<LocalCardChapterDTO> chapters = generateChapters( model, request );

        if ( nbChapter > 1 )
        {
            chapters.remove( chapters.size( ) - 1 );
            nbChapter = nbChapter - 1;
        }

        model.put( MARK_FICHE_LOCALE, _cardBean );
        model.put( PARAMETER_CHAPTER_ID, nbChapter );
        model.put( PARAMETER_CHAPTERS_LIST, chapters );
        setDataInModel( request, model );

        return getAdminPage( getTemplateFiche( model ) );
    }

    /**
     * Add a lien
     * @param request the page request
     * @return the ManageFicheLocale page
     */
    public String doAddLien( HttpServletRequest request )
    {
        Integer nbLien = Integer.valueOf( request.getParameter( PARAMETER_LINK_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _folderBean, request );

        List<LocalFolderLinkDTO> links = generateLinks( model, request );
        LocalFolderLinkDTO link = new LocalFolderLinkDTO( );
        link.setPosition( nbLien + 1 );
        links.add( link );

        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        model.put( PARAMETER_LINK_ID, nbLien + 1L );
        model.put( PARAMETER_LINK_LIST, links );
        setDataInModel( request, model );

        return getAdminPage( getTemplateDossier( model ) );
    }

    /**
     * Delete a chapter
     * @param request the page request
     * @return the ManageFicheLocale page
     */
    public String doDeleteLien( HttpServletRequest request )
    {
        Integer nbLien = Integer.valueOf( request.getParameter( PARAMETER_LINK_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _folderBean, request );

        List<LocalFolderLinkDTO> links = generateLinks( model, request );
        links.remove( links.size( ) - 1 );

        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        model.put( PARAMETER_LINK_ID, nbLien - 1 );
        model.put( PARAMETER_LINK_LIST, links );
        setDataInModel( request, model );

        return getAdminPage( getTemplateDossier( model ) );
    }

    /**
     * Verify if a theme exists
     * @param request the page request
     * @return the CreateDossierLocal page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyThemeParent( HttpServletRequest request )
    {
        IPluginActionResult result = new DefaultPluginActionResult( );
        HashMap<String, Object> model = new HashMap<String, Object>( );

        populate( _folderBean, request );

        String errorKey = _dilaValidationService.validateRootTheme( _folderBean );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
            return result;
        }

        generateLinks( model, request );
        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        setDataInModel( request, model );

        result.setHtmlContent( getAdminPage( getTemplateDossier( model ) ) );
        return result;
    }

    /**
     * Verify if a dossier exists
     * @param request the page request
     * @return the CreateDossierLocal page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyDossierFrere( HttpServletRequest request )
    {
        IPluginActionResult result = new DefaultPluginActionResult( );
        HashMap<String, Object> model = new HashMap<String, Object>( );

        populate( _folderBean, request );

        String errorKey = _dilaValidationService.validateLinkedFolder( _folderBean );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
            return result;
        }

        generateLinks( model, request );
        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        setDataInModel( request, model );

        result.setHtmlContent( getAdminPage( getTemplateDossier( model ) ) );
        return result;
    }

    /**
     * Verify if a fiche exists
     * @param request the page request
     * @return the CreateDossierLocal page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyFicheLien( HttpServletRequest request )
    {
        String referenceId = request.getParameter( PARAMETER_REFERENCE_ID );
        populate( _folderBean, request );
        IPluginActionResult result = new DefaultPluginActionResult( );
        HashMap<String, Object> model = new HashMap<String, Object>( );
        LocalFolderLinkDTO link = new LocalFolderLinkDTO( );
        String errorKey = _dilaValidationService.validateLinkCard( _folderBean, referenceId, link );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
            return result;
        }

        generateLinks( model, request );
        model.put( MARK_DOSSIER_LOCAL, _folderBean );
        setDataInModel( request, model );

        result.setHtmlContent( getAdminPage( getTemplateDossier( model ) ) );
        return result;
    }

    /**
     * Verify if a dossier exists
     * @param request the page request
     * @return the CreateFicheLocale page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyDossierParent( HttpServletRequest request )
    {
        populate( _cardBean, request );

        IPluginActionResult result = new DefaultPluginActionResult( );
        HashMap<String, Object> model = new HashMap<String, Object>( );

        String errorKey = _dilaValidationService.validateRootFolder( _cardBean, _cardBean.getParentFolderId( ) );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
            return result;
        }

        generateChapters( model, request );

        model.put( MARK_FICHE_LOCALE, _cardBean );
        setDataInModel( request, model );

        result.setHtmlContent( getAdminPage( getTemplateFiche( model ) ) );
        return result;
    }

    /**
     * Verify if a fiche exists
     * @param request the page request
     * @return the CreateFicheLocale page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyFicheSoeur( HttpServletRequest request )
    {
        populate( _cardBean, request );
        IPluginActionResult result = new DefaultPluginActionResult( );
        HashMap<String, Object> model = new HashMap<String, Object>( );

        String errorKey = _dilaValidationService.validateLinkedCard( _cardBean, _cardBean.getSiblingCardId( ) );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
            return result;
        }

        generateChapters( model, request );
        model.put( MARK_FICHE_LOCALE, _cardBean );
        setDataInModel( request, model );
        result.setHtmlContent( getAdminPage( getTemplateFiche( model ) ) );
        return result;
    }

    /**
     * Create a fiche locale
     * @param request the page request
     * @return the ManageFicheLocale page or an {@link AdminMessage}
     */
    public String doCreateFicheLocale( HttpServletRequest request )
    {
        populate( _cardBean, request );
        List<LocalCardChapterDTO> chapters = generateChapters( null, request );

        String errorKey = _dilaValidationService.validateLocalCard( _cardBean, chapters );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            return AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP );
        }

        // Create the fiche locale
        if ( StringUtils.isNumeric( _cardBean.getParentFolderId( ) ) )
        {
            _cardBean.getLocalDTO( ).setBreadCrumb( _cardBean.getLocalParentFolder( ).getLocalDTO( ).getBreadCrumb( ) );

        }
        else
        {
            _cardBean.getLocalDTO( ).setBreadCrumb( _cardBean.getNationalParentFolder( ).getBreadcrumb( ) );
        }

        _cardBean.getLocalDTO( ).setXml( XMLConvertor.convertCardToXML( _cardBean, chapters ) );
        Long localId = _dilaLocalService.create( _cardBean.getLocalDTO( ), false );
        _cardBean.getLocalDTO( ).setId( localId );

        Long ficheId = _dilaLocalCardService.create( _cardBean );
        _cardBean.setId( ficheId );

        // Create chapters
        for ( LocalCardChapterDTO chapitre : chapters )
        {
            chapitre.setLocalCard( _cardBean );
            _dilaLocalCardChapterService.create( chapitre );
        }

        return JSP_MANAGE_LOCAL_NAME;
    }

    /**
     * Create a fiche locale
     * @param request the page request
     * @return the ManageFicheLocale page or an {@link AdminMessage}
     */
    public String doModifyFicheLocale( HttpServletRequest request )
    {
        populate( _cardBean, request );
        List<LocalCardChapterDTO> chapters = generateChapters( null, request );

        String errorKey = _dilaValidationService.validateLocalCard( _cardBean, chapters );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            return AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP );
        }

        // Create the fiche locale
        if ( StringUtils.isNumeric( _cardBean.getParentFolderId( ) ) )
        {
            _cardBean.getLocalDTO( ).setBreadCrumb( _cardBean.getLocalParentFolder( ).getLocalDTO( ).getBreadCrumb( ) );
        }
        else
        {
            _cardBean.getLocalDTO( ).setBreadCrumb( _cardBean.getNationalParentFolder( ).getBreadcrumb( ) );
        }

        _cardBean.getLocalDTO( ).setXml( XMLConvertor.convertCardToXML( _cardBean, chapters ) );
        _dilaLocalService.update( _cardBean.getLocalDTO( ), false );

        // Update fiche
        _dilaLocalCardService.update( _cardBean );

        // Delete old chapters
        _dilaLocalCardChapterService.deleteByCardId( _cardBean.getId( ) );

        // Create chapters
        for ( LocalCardChapterDTO chapitre : chapters )
        {
            chapitre.setLocalCard( _cardBean );
            _dilaLocalCardChapterService.create( chapitre );
        }

        return JSP_MANAGE_LOCAL_NAME;
    }

    /**
     * Create a dossier local
     * @param request the page request
     * @return the ManageFicheLocale page or an {@link AdminMessage}
     */
    public String doCreateDossierLocal( HttpServletRequest request )
    {
        populate( _folderBean, request );
        List<LocalFolderLinkDTO> links = generateLinks( null, request );

        String errorKey = _dilaValidationService.validateLocalFolder( _folderBean, links );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            return AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP );
        }

        // generate XML 
        _folderBean.getLocalDTO( ).setXml( XMLConvertor.convertDossierInXML( _folderBean, links ) );
        Long localId = _dilaLocalService.create( _folderBean.getLocalDTO( ), true );
        _folderBean.getLocalDTO( ).setId( localId );

        Long dossierId = _dilaLocalFolderService.create( _folderBean );
        _folderBean.setId( dossierId );

        for ( LocalFolderLinkDTO link : links )
        {
            link.setLocalFolderId( _folderBean.getId( ) );
            _dilaLocalFolderLinkService.create( link );
        }

        return JSP_MANAGE_LOCAL_NAME;
    }

    /**
     * Create a dossier local
     * @param request the page request
     * @return the ManageFicheLocale page or an {@link AdminMessage}
     */
    public String doModifyDossierLocal( HttpServletRequest request )
    {
        populate( _folderBean, request );
        List<LocalFolderLinkDTO> links = generateLinks( null, request );

        String errorKey = _dilaValidationService.validateLocalFolder( _folderBean, links );

        if ( StringUtils.isNotBlank( errorKey ) )
        {
            return AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP );
        }

        // generate XML 
        _folderBean.getLocalDTO( ).setXml( XMLConvertor.convertDossierInXML( _folderBean, links ) );

        // update local
        _dilaLocalService.update( _folderBean.getLocalDTO( ), true );

        // update dossier local
        _dilaLocalFolderService.update( _folderBean );

        // delete old links
        _dilaLocalFolderLinkService.deleteByFolderId( _folderBean.getId( ) );

        // create new links
        for ( LocalFolderLinkDTO link : links )
        {
            link.setLocalFolderId( _folderBean.getId( ) );
            _dilaLocalFolderLinkService.create( link );
        }

        return JSP_MANAGE_LOCAL_NAME;
    }

    /**
     * Returns the confirmation message to delete a DILA fiche locale
     * @param request The Http request
     * @return the html code message
     */
    public String getDeleteFicheLocale( HttpServletRequest request )
    {
        String idLocal = request.getParameter( DilaConstants.MARK_LOCAL_ID );

        if ( StringUtils.isEmpty( idLocal ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        Map<String, Object> urlParam = new HashMap<String, Object>( );
        urlParam.put( DilaConstants.MARK_LOCAL_ID, idLocal );

        return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_CONFIRMATION_DELETE_FICHE, null,
                DilaConstants.MESSAGE_TITLE_DELETE_FICHE, DilaConstants.JSP_DELETE_FICHE, "_self",
                AdminMessage.TYPE_CONFIRMATION, urlParam, DilaConstants.JSP_MANAGE_LOCAL );
    }

    /**
     * Delete a DILA fiche locale.
     * @param request The Http request
     * @return url return
     */
    public String doDeleteFicheLocale( HttpServletRequest request )
    {
        String idLocal = request.getParameter( DilaConstants.MARK_LOCAL_ID );

        if ( StringUtils.isEmpty( idLocal ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        Long ficheLocalId = _dilaLocalCardService.findCardIdByLocalId( idLocal );

        _dilaLocalCardChapterService.deleteByCardId( ficheLocalId );
        _dilaLocalCardService.delete( ficheLocalId );
        _dilaLocalService.delete( idLocal );

        return AppPathService.getBaseUrl( request ) + DilaConstants.JSP_MANAGE_LOCAL;
    }

    /**
     * Returns the confirmation message to delete a DILA fiche locale
     * @param request The Http request
     * @return the html code message
     */
    public String getDeleteDossierLocal( HttpServletRequest request )
    {
        String idLocal = request.getParameter( DilaConstants.MARK_LOCAL_ID );

        if ( StringUtils.isEmpty( idLocal ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        // Check if can delete dossier
        if ( _dilaLocalCardService.isCardWithParentId( idLocal ) )
        {
            return AdminMessageService.getMessageUrl( request, "dila.message.impossible.delete_dossierlocal",
                    AdminMessage.TYPE_STOP );
        }

        Map<String, Object> urlParam = new HashMap<String, Object>( );
        urlParam.put( DilaConstants.MARK_LOCAL_ID, idLocal );

        return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_CONFIRMATION_DELETE_DOSSIER, null,
                DilaConstants.MESSAGE_TITLE_DELETE_DOSSIER, DilaConstants.JSP_DELETE_DOSSIER, "_self",
                AdminMessage.TYPE_CONFIRMATION, urlParam, DilaConstants.JSP_MANAGE_STYLESHEET );
    }

    /**
     * Delete a fiche locale dossier local.
     * @param request The Http request
     * @return url return
     */
    public String doDeleteDossierLocal( HttpServletRequest request )
    {
        String idLocal = request.getParameter( DilaConstants.MARK_LOCAL_ID );

        if ( StringUtils.isEmpty( idLocal ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        Long dossierLocalId = _dilaLocalFolderService.findFolderIdByLocalId( idLocal );

        _dilaLocalFolderLinkService.deleteByFolderId( dossierLocalId );
        _dilaLocalFolderService.delete( dossierLocalId );
        _dilaLocalService.delete( idLocal );

        return AppPathService.getBaseUrl( request ) + DilaConstants.JSP_MANAGE_LOCAL;
    }

    /**
     * Set data in model
     * @param request the page request
     * @param model the model to populate
     */
    private void setDataInModel( HttpServletRequest request, Map<String, Object> model )
    {
        List<AudienceDTO> audienceList = _dilaAudienceService.findAll( );
        ReferenceList listTypeContenu = ListUtils.toReferenceList( audienceList, "id", "label", null );
        model.put( MARK_AUDIENCE, listTypeContenu );
        model.put( MARK_LOCALE, getLocale( ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
    }

    /**
     * Generate chapters to display
     * @param model the model
     * @param request the page request
     * @return list of chapters
     */
    private List<LocalCardChapterDTO> generateChapters( Map<String, Object> model, HttpServletRequest request )
    {
        Integer nbChapter = Integer.valueOf( request.getParameter( PARAMETER_CHAPTER_ID ) );

        List<LocalCardChapterDTO> chapters = new ArrayList<LocalCardChapterDTO>( );

        for ( int i = 1; i <= nbChapter; i++ )
        {
            String titre = request.getParameter( "chapter_title_" + i );
            String contenu = request.getParameter( "chapter_content_" + i );

            LocalCardChapterDTO chapter = new LocalCardChapterDTO( );
            chapter.setTitle( titre );
            chapter.setContent( contenu );
            chapter.setPosition( i );

            chapters.add( chapter );
        }

        if ( model != null )
        {
            model.put( PARAMETER_CHAPTER_ID, nbChapter );
            model.put( PARAMETER_CHAPTERS_LIST, chapters );
        }

        return chapters;
    }

    /**
     * Generate links to display
     * @param model the model
     * @param request the page request
     * @return list of links
     */
    private List<LocalFolderLinkDTO> generateLinks( Map<String, Object> model, HttpServletRequest request )
    {
        Integer nbLinks = Integer.valueOf( request.getParameter( PARAMETER_LINK_ID ) );

        List<LocalFolderLinkDTO> liens = new ArrayList<LocalFolderLinkDTO>( );

        for ( int i = 1; i <= nbLinks; i++ )
        {
            String titre = request.getParameter( "link_title_" + i );
            String referenceId = request.getParameter( "link_reference_" + i );

            LocalFolderLinkDTO lien = new LocalFolderLinkDTO( );
            lien.setTitle( titre );
            lien.setCardId( referenceId );
            lien.setPosition( i );
            _dilaValidationService.validateLinkCard( _folderBean, referenceId, lien );
            liens.add( lien );
        }

        if ( model != null )
        {
            model.put( PARAMETER_LINK_ID, nbLinks );
            model.put( PARAMETER_LINK_LIST, liens );
        }

        return liens;
    }

    /**
     * Filtre list of {@link LocalDTO}
     * @param initList the initial list
     * @param type the type of resource
     * @param id the id od resource
     * @param name the name of resource
     * @param author the author of resource
     * @return the filtered list
     */
    private List<LocalDTO> filterLocal( List<LocalDTO> initList, Long type, Long id, String name, String author )
    {
        List<LocalDTO> result = new ArrayList<LocalDTO>( );

        for ( LocalDTO local : initList )
        {
            if ( !type.equals( 0L ) && !local.getType( ).getId( ).equals( type ) )
            {
                continue;
            }
            if ( !id.equals( 0L ) && !local.getId( ).equals( id ) )
            {
                continue;
            }
            if ( StringUtils.isNotBlank( name ) && !local.getTitle( ).equals( name ) )
            {
                continue;
            }
            if ( StringUtils.isNotBlank( author ) && !local.getAuthor( ).equals( author ) )
            {
                continue;
            }
            result.add( local );
        }

        return result;
    }

    /**
     * Get the current template for fiche
     * @param model the template model
     * @return the current html template
     */
    private String getTemplateFiche( Map<String, Object> model )
    {
        if ( ActionTypeEnum.CREATE.getValue( ).equals( _strAction ) )
        {
            return AppTemplateService.getTemplate( TEMPLATE_CREATE_LOCAL_CARD, getLocale( ), model ).getHtml( );
        }
        else
        {
            return AppTemplateService.getTemplate( TEMPLATE_MODIFY_LOCAL_CARD, getLocale( ), model ).getHtml( );
        }
    }

    /**
     * Get the current template for dossier
     * @param model the template model
     * @return the current html template
     */
    private String getTemplateDossier( Map<String, Object> model )
    {
        if ( ActionTypeEnum.CREATE.getValue( ).equals( _strAction ) )
        {
            return AppTemplateService.getTemplate( TEMPLATE_CREATE_LOCAL_FOLDER, getLocale( ), model ).getHtml( );
        }
        else
        {
            return AppTemplateService.getTemplate( TEMPLATE_MODIFY_LOCAL_FOLDER, getLocale( ), model ).getHtml( );
        }
    }
}
