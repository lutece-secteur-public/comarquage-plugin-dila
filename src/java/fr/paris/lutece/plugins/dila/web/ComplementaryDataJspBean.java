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

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLearnMoreDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataTeleserviceDTO;
import fr.paris.lutece.plugins.dila.business.enums.ActionTypeEnum;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;
import fr.paris.lutece.plugins.dila.business.enums.ResourceTypeEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.AudienceDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.IDilaAudienceService;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataLinkService;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataService;
import fr.paris.lutece.plugins.dila.service.IDilaXmlService;
import fr.paris.lutece.plugins.dila.utils.ListUtils;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.portal.web.pluginaction.DefaultPluginActionResult;
import fr.paris.lutece.portal.web.pluginaction.IPluginActionResult;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.sort.AttributeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * ComplementaryDataJspBean
 */
public class ComplementaryDataJspBean extends PluginAdminPageJspBean
{
    //Rights
    public static final String RIGHT_MANAGE_COMPLEMENTARY_DATA = "DILA_COMPLEMENTARY_DATA_MANAGEMENT";

    /** Serial id */
    private static final long serialVersionUID = -1328940389092455815L;

    //templates
    private static final String TEMPLATE_MANAGE_COMPLEMENTARY_DATA = "admin/plugins/dila/manage_donnee-complementaire.html";
    private static final String TEMPLATE_CREATE_COMPLEMENTARY_DATA = "admin/plugins/dila/create_donnee-complementaire.html";
    private static final String TEMPLATE_MODIFY_COMPLEMENTARY_DATA = "admin/plugins/dila/modify_donnee-complementaire.html";

    //jsp
    private static final String JSP_MANAGE_COMPLEMENTARY_DATA = "ManageComplementaryData.jsp";

    // parameters
    private static final String PARAMETER_TELESERVICE_ID = "id_teleservice";
    private static final String PARAMETER_LINK_ID = "id_lien";

    // marks
    private static final String MARK_COMPLEMENTARY_DATA = "donneesComplementaires";
    private static final String MARK_TELESERVICE_LIST = "list_teleservices";
    private static final String MARK_LINK_LIST = "list_liens";
    private static final String MARK_AUDIENCE = "audiences";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";

    // Beans
    private ComplementaryDataDTO _complementaryData;
    private String _strAction;

    // Constants
    private int _nItemsPerPage;
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private IDilaComplementaryDataService _dilaComplementaryDataService = SpringContextService
            .getBean( "dilaComplementaryDataService" );
    private IDilaComplementaryDataLinkService _dilaComplementaryDataLinkService = SpringContextService
            .getBean( "dilaComplementaryDataLinkService" );
    private IDilaXmlService _dilaXmlService = SpringContextService.getBean( "dilaXmlService" );
    private IDilaAudienceService _dilaAudienceService = SpringContextService.getBean( "dilaAudienceService" );

    /**
     * Return complementary data management
     * @param request The Http request
     * @return Html
     */
    public String getManageComplementaryData( HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>( );
        String strId = request.getParameter( DilaConstants.MARK_ID );
        String strDataName = request.getParameter( DilaConstants.MARK_NAME );

        //Init completed cards data table 
        List<ComplementaryDataDTO> listComplementaryData = _dilaComplementaryDataService.findAll( );
        List<ComplementaryDataDTO> listToDisplay = filtrer( listComplementaryData, strDataName, strId );

        // Orders the list of result and add pagination
        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );

            Collections.sort( listToDisplay, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
        }

        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( DilaConstants.PROPERTY_STYLESHEETS_PER_PAGE, 50 );
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        String strURL = getHomeUrl( request );

        if ( strSortedAttributeName != null )
        {
            strURL += ( "?" + Parameters.SORTED_ATTRIBUTE_NAME + "=" + strSortedAttributeName );
        }

        if ( strAscSort != null )
        {
            strURL += ( "&" + Parameters.SORTED_ASC + "=" + strAscSort );
        }

        LocalizedPaginator<ComplementaryDataDTO> paginator = new LocalizedPaginator<ComplementaryDataDTO>(
                listToDisplay, _nItemsPerPage, strURL, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex,
                getLocale( ) );

        model.put( DilaConstants.MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( DilaConstants.MARK_PAGINATOR, paginator );
        model.put( DilaConstants.MARK_COMP_DATA_LIST, paginator.getPageItems( ) );
        model.put( DilaConstants.MARK_ID, strId );
        model.put( DilaConstants.MARK_NAME, strDataName );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_COMPLEMENTARY_DATA, getLocale( ),
                model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Return complementary data creation page
     * @param request The Http request
     * @return Html
     */
    public String getCreateComplementaryData( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.CREATE.getValue( );

        Map<String, Object> model = new HashMap<String, Object>( );

        _complementaryData = new ComplementaryDataDTO( );

        List<ComplementaryDataTeleserviceDTO> listTeleservice = new ArrayList<ComplementaryDataTeleserviceDTO>( );
        List<ComplementaryDataLearnMoreDTO> listLink = new ArrayList<ComplementaryDataLearnMoreDTO>( );

        model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
        model.put( MARK_TELESERVICE_LIST, listTeleservice );
        model.put( PARAMETER_TELESERVICE_ID, 0 );
        model.put( MARK_LINK_LIST, listLink );
        model.put( PARAMETER_LINK_ID, 0 );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_CREATE_COMPLEMENTARY_DATA, getLocale( ),
                model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Return complementary data modification page
     * @param request The Http request
     * @return Html
     */
    public String getModifyComplementaryData( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.MODIFY.getValue( );

        String idComplementaryData = request.getParameter( DilaConstants.MARK_ID );

        if ( StringUtils.isEmpty( idComplementaryData ) || !StringUtils.isNumeric( idComplementaryData ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        _complementaryData = _dilaComplementaryDataService.findById( Long.valueOf( idComplementaryData ) );

        if ( _complementaryData == null )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        // get card infos
        verifyCard( _complementaryData.getCard( ).getIdXml( ) );

        Map<String, Object> model = new HashMap<String, Object>( );

        List<ComplementaryDataLinkDTO> listTeleservice = _dilaComplementaryDataLinkService.findByDataId(
                Long.valueOf( idComplementaryData ), ComplementaryLinkTypeEnum.TELESERVICE );
        List<ComplementaryDataLinkDTO> listLink = _dilaComplementaryDataLinkService.findByDataId(
                Long.valueOf( idComplementaryData ), ComplementaryLinkTypeEnum.LEARN_MORE );

        model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
        model.put( MARK_TELESERVICE_LIST, listTeleservice );
        model.put( PARAMETER_TELESERVICE_ID, listTeleservice.size( ) );
        model.put( MARK_LINK_LIST, listLink );
        model.put( PARAMETER_LINK_ID, listLink.size( ) );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MODIFY_COMPLEMENTARY_DATA, getLocale( ),
                model );

        return getAdminPage( templateList.getHtml( ) );
    }

    /**
     * Returns the confirmation message to delete a complementary data
     * @param request The Http request
     * @return the html code message
     * @throws AccessDeniedException AccessDeniedException
     */
    public String getDeleteComplementaryData( HttpServletRequest request ) throws AccessDeniedException
    {
        String id = request.getParameter( DilaConstants.MARK_ID );

        if ( StringUtils.isEmpty( id ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        Map<String, Object> urlParam = new HashMap<String, Object>( );
        urlParam.put( DilaConstants.MARK_ID, id );

        return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_CONFIRMATION_DELETE_DONNEE, null,
                DilaConstants.MESSAGE_TITLE_DELETE_DONNEE, DilaConstants.JSP_DELETE_DONNEE, "_self",
                AdminMessage.TYPE_CONFIRMATION, urlParam, DilaConstants.JSP_MANAGE_DONNEES );
    }

    /**
     * Add a teleservice
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doAddTeleservice( HttpServletRequest request )
    {
        Integer nbTeleservice = Integer.valueOf( request.getParameter( PARAMETER_TELESERVICE_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _complementaryData, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> links = getExistingLinks( request );

        ComplementaryDataTeleserviceDTO teleservice = new ComplementaryDataTeleserviceDTO( );
        teleservice.setPosition( nbTeleservice + 1 );
        teleservices.add( teleservice );

        model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
        model.put( PARAMETER_TELESERVICE_ID, nbTeleservice + 1 );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LINK_ID, links.size( ) );
        model.put( MARK_LINK_LIST, links );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Delete a teleservice
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doDeleteTeleservice( HttpServletRequest request )
    {
        Integer nbTeleservice = Integer.valueOf( request.getParameter( PARAMETER_TELESERVICE_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _complementaryData, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> links = getExistingLinks( request );
        teleservices.remove( teleservices.size( ) - 1 );

        model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
        model.put( PARAMETER_TELESERVICE_ID, nbTeleservice - 1 );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LINK_ID, links.size( ) );
        model.put( MARK_LINK_LIST, links );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Add a link
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doAddLink( HttpServletRequest request )
    {
        Integer nbLink = Integer.valueOf( request.getParameter( PARAMETER_LINK_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _complementaryData, request );

        List<ComplementaryDataLinkDTO> liens = getExistingLinks( request );
        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );

        ComplementaryDataLearnMoreDTO lien = new ComplementaryDataLearnMoreDTO( );
        lien.setPosition( nbLink + 1 );
        liens.add( lien );

        model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
        model.put( PARAMETER_TELESERVICE_ID, teleservices.size( ) );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LINK_ID, nbLink + 1 );
        model.put( MARK_LINK_LIST, liens );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Delete a link
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doDeleteLink( HttpServletRequest request )
    {
        Integer nbLink = Integer.valueOf( request.getParameter( PARAMETER_LINK_ID ) );
        Map<String, Object> model = new HashMap<String, Object>( );

        populate( _complementaryData, request );

        List<ComplementaryDataLinkDTO> links = getExistingLinks( request );
        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        links.remove( links.size( ) - 1 );

        model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
        model.put( PARAMETER_TELESERVICE_ID, teleservices.size( ) );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LINK_ID, nbLink - 1 );
        model.put( MARK_LINK_LIST, links );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Verify if a card exists
     * @param request the page request
     * @return the CreateDonneeComplementaire page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyCard( HttpServletRequest request )
    {
        populate( _complementaryData, request );

        IPluginActionResult result = new DefaultPluginActionResult( );
        HashMap<String, Object> model = new HashMap<String, Object>( );

        String errorKey = verifyCard( _complementaryData.getCard( ).getIdXml( ) );

        if ( errorKey != null )
        {
            // if error, we return the AdminMessage
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
        }
        else
        {
            // Else, we display the Create Donnee page
            List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
            List<ComplementaryDataLinkDTO> links = getExistingLinks( request );

            model.put( MARK_COMPLEMENTARY_DATA, _complementaryData );
            model.put( MARK_TELESERVICE_LIST, teleservices );
            model.put( PARAMETER_TELESERVICE_ID, teleservices.size( ) );
            model.put( PARAMETER_LINK_ID, links.size( ) );
            model.put( MARK_LINK_LIST, links );
            setDataInModel( request, model );

            HtmlTemplate templateList;
            templateList = AppTemplateService.getTemplate( TEMPLATE_CREATE_COMPLEMENTARY_DATA, getLocale( ), model );
            result.setHtmlContent( getAdminPage( templateList.getHtml( ) ) );
        }

        return result;
    }

    /**
     * Create a complementary data
     * @param request the page request
     * @return the ManageComplementaryData page or an {@link AdminMessage}
     */
    public String doCreateComplementaryData( HttpServletRequest request )
    {
        String result = null;
        populate( _complementaryData, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> links = getExistingLinks( request );

        String errorKeyCard = verifyCard( _complementaryData.getCard( ).getIdXml( ) );
        String errorKeyTeleservice = validateLinks( teleservices );
        String errorKeyLinks = validateLinks( links );

        if ( StringUtils.isNotBlank( errorKeyCard ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyCard, AdminMessage.TYPE_STOP );
        }
        else if ( StringUtils.isNotBlank( errorKeyTeleservice ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyTeleservice, AdminMessage.TYPE_STOP );
        }
        else if ( StringUtils.isNotBlank( errorKeyLinks ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyLinks, AdminMessage.TYPE_STOP );
        }

        if ( result != null )
        {
            // in case of error, stop the process
            return result;
        }

        Long id = _dilaComplementaryDataService.create( _complementaryData );

        for ( ComplementaryDataLinkDTO teleservice : teleservices )
        {
            teleservice.setIdComplementaryData( id );
            _dilaComplementaryDataLinkService.create( teleservice );
        }

        for ( ComplementaryDataLinkDTO link : links )
        {
            link.setIdComplementaryData( id );
            _dilaComplementaryDataLinkService.create( link );
        }

        return JSP_MANAGE_COMPLEMENTARY_DATA;
    }

    /**
     * Modify a complemantary data
     * @param request the page request
     * @return the ManageComplementaryData page or an {@link AdminMessage}
     */
    public String doModifyComplementaryData( HttpServletRequest request )
    {
        String result = null;

        populate( _complementaryData, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> links = getExistingLinks( request );

        String errorKeyTeleservice = validateLinks( teleservices );
        String errorKeyLinks = validateLinks( links );

        if ( StringUtils.isNotBlank( errorKeyTeleservice ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyTeleservice, AdminMessage.TYPE_STOP );
        }
        else if ( StringUtils.isNotBlank( errorKeyLinks ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyLinks, AdminMessage.TYPE_STOP );
        }

        if ( result != null )
        {
            // in case of error, stop the treatment
            return result;
        }

        _dilaComplementaryDataService.update( _complementaryData );

        _dilaComplementaryDataLinkService.deleteFromComplementaryData( _complementaryData.getId( ),
                ComplementaryLinkTypeEnum.TELESERVICE );

        for ( ComplementaryDataLinkDTO teleservice : teleservices )
        {
            teleservice.setIdComplementaryData( _complementaryData.getId( ) );
            _dilaComplementaryDataLinkService.create( teleservice );
        }

        _dilaComplementaryDataLinkService.deleteFromComplementaryData( _complementaryData.getId( ),
                ComplementaryLinkTypeEnum.LEARN_MORE );

        for ( ComplementaryDataLinkDTO link : links )
        {
            link.setIdComplementaryData( _complementaryData.getId( ) );
            _dilaComplementaryDataLinkService.create( link );
        }

        return JSP_MANAGE_COMPLEMENTARY_DATA;
    }

    /**
     * Delete a DILA complementary data.
     * @param request The Http request
     * @return url return
     * @throws AccessDeniedException AccessDeniedException
     */
    public String doDeleteComplementaryData( HttpServletRequest request ) throws AccessDeniedException
    {
        String id = request.getParameter( DilaConstants.MARK_ID );

        if ( StringUtils.isEmpty( id ) || !StringUtils.isNumeric( id ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        _dilaComplementaryDataLinkService.deleteFromComplementaryData( Long.valueOf( id ),
                ComplementaryLinkTypeEnum.TELESERVICE );
        _dilaComplementaryDataLinkService.deleteFromComplementaryData( Long.valueOf( id ),
                ComplementaryLinkTypeEnum.LEARN_MORE );
        _dilaComplementaryDataService.delete( Long.valueOf( id ) );

        return AppPathService.getBaseUrl( request ) + DilaConstants.JSP_MANAGE_DONNEES;
    }

    /**
     * Check validity of card id
     * @param id the id to check
     * @return the error key or null
     */
    private String verifyCard( String id )
    {
        String result = null;

        if ( StringUtils.isBlank( _complementaryData.getCard( ).getIdXml( ) )
                || ( _complementaryData.getCard( ).getIdXml( ).length( ) > 255 ) )
        {
            result = "dila.create_donnee.error.ficheFormat";
        }
        else if ( _dilaComplementaryDataService.cardHasComplement( _complementaryData.getCard( ).getIdXml( ) ) )
        {
            result = "dila.create_donnee.error.ficheExistante";
        }
        else
        {
            XmlDTO cardLink = null;

            if ( _complementaryData.getCard( ).getIdXml( ).startsWith( "F" ) )
            {
                // Search in XML folder
                List<String> availableTypes = new ArrayList<String>( );
                availableTypes.add( ResourceTypeEnum.CARD.getLabel( ) );

                cardLink = _dilaXmlService.findByIdAndTypesAndAudience( _complementaryData.getCard( ).getIdXml( ),
                        availableTypes, _complementaryData.getIdAudience( ) );
            }

            if ( cardLink == null )
            {
                result = "dila.create_donnee.error.ficheIntrouvable";
            }
            else
            {
                _complementaryData.setCard( cardLink );
            }
        }

        return result;
    }

    /**
     * Get actual list of teleservices
     * @param request the page request
     * @return the populate list of {@link ComplementaryDataTeleserviceDTO}
     */
    private List<ComplementaryDataLinkDTO> getExistingTeleservices( HttpServletRequest request )
    {
        Integer nbTeleservice = Integer.valueOf( request.getParameter( PARAMETER_TELESERVICE_ID ) );
        List<ComplementaryDataLinkDTO> teleservices = new ArrayList<ComplementaryDataLinkDTO>( );

        for ( int i = 1; i <= nbTeleservice; i++ )
        {
            String title = request.getParameter( "teleservice_title_" + i );
            String url = request.getParameter( "teleservice_url_" + i );

            ComplementaryDataTeleserviceDTO teleservice = new ComplementaryDataTeleserviceDTO( );
            teleservice.setTitle( title );
            teleservice.setURL( url );
            teleservice.setPosition( i );

            teleservices.add( teleservice );
        }

        return teleservices;
    }

    /**
     * Get actual list of teleservices
     * @param request the page request
     * @return the populate list of {@link ComplementaryDataTeleserviceDTO}
     */
    private List<ComplementaryDataLinkDTO> getExistingLinks( HttpServletRequest request )
    {
        Integer nbLinks = Integer.valueOf( request.getParameter( PARAMETER_LINK_ID ) );
        List<ComplementaryDataLinkDTO> links = new ArrayList<ComplementaryDataLinkDTO>( );

        for ( int i = 1; i <= nbLinks; i++ )
        {
            String title = request.getParameter( "lien_title_" + i );
            String url = request.getParameter( "lien_url_" + i );

            ComplementaryDataLearnMoreDTO link = new ComplementaryDataLearnMoreDTO( );
            link.setTitle( title );
            link.setURL( url );
            link.setPosition( i );

            links.add( link );
        }

        return links;
    }

    /**
     * Check the list of links
     * @param links list to check
     * @return error key or null
     */
    private String validateLinks( List<ComplementaryDataLinkDTO> links )
    {
        String result = null;

        for ( ComplementaryDataLinkDTO link : links )
        {
            if ( StringUtils.isBlank( link.getTitle( ) ) )
            {
                if ( link.getType( ).equals( ComplementaryLinkTypeEnum.TELESERVICE ) )
                {
                    result = "dila.create_donnee.error.teleserviceTitre";
                }
                else
                {
                    result = "dila.create_donnee.error.lienTitre";
                }

                break;
            }
            else if ( StringUtils.isBlank( link.getURL( ) ) )
            {
                if ( link.getType( ).equals( ComplementaryLinkTypeEnum.TELESERVICE ) )
                {
                    result = "dila.create_donnee.error.teleserviceURL";
                }
                else
                {
                    result = "dila.create_donnee.error.lienURL";
                }

                break;
            }
        }

        return result;
    }

    /**
     * Filter list with param
     * @param listComplementaryData original list
     * @param strDataName card title
     * @param strId card id
     * @return filtered list
     */
    private List<ComplementaryDataDTO> filtrer( List<ComplementaryDataDTO> listComplementaryData, String strDataName,
            String strId )
    {
        List<ComplementaryDataDTO> result = new ArrayList<ComplementaryDataDTO>( );

        for ( ComplementaryDataDTO data : listComplementaryData )
        {
            if ( StringUtils.isNotBlank( strDataName ) && !data.getCard( ).getTitle( ).equals( strDataName ) )
            {
                continue;
            }

            if ( StringUtils.isNotBlank( strId ) && !data.getCard( ).getIdXml( ).equals( strId ) )
            {
                continue;
            }

            result.add( data );
        }

        return result;
    }

    /**
     * Set data in model
     * @param request the page request
     * @param model the model to populate
     */
    private void setDataInModel( HttpServletRequest request, Map<String, Object> model )
    {
        List<AudienceDTO> audienceList = _dilaAudienceService.findAll( );
        ReferenceList listContentType = ListUtils.toReferenceList( audienceList, "id", "label", null );
        model.put( MARK_AUDIENCE, listContentType );
        model.put( MARK_LOCALE, getLocale( ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
    }

    /**
     * Get the current template
     * @param model the template model
     * @return the current html template
     */
    private String getTemplate( Map<String, Object> model )
    {
        if ( ActionTypeEnum.CREATE.getValue( ).equals( _strAction ) )
        {
            return AppTemplateService.getTemplate( TEMPLATE_CREATE_COMPLEMENTARY_DATA, getLocale( ), model ).getHtml( );
        }
        else
        {
            return AppTemplateService.getTemplate( TEMPLATE_MODIFY_COMPLEMENTARY_DATA, getLocale( ), model ).getHtml( );
        }
    }
}
