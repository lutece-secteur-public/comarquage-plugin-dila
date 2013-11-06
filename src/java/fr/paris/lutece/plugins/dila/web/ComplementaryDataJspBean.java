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

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


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
    private static final String TEMPLATE_CREATE_DONNEE_COMPLEMENTAIRE = "admin/plugins/dila/create_donnee-complementaire.html";
    private static final String TEMPLATE_MODIFY_DONNEE_COMPLEMENTAIRE = "admin/plugins/dila/modify_donnee-complementaire.html";

    //jsp
    private static final String JSP_MANAGE_DONNEE_COMPLEMENTAIRE = "ManageDonneeComplementaire.jsp";

    // parameters
    private static final String PARAMETER_TELESERVICE_ID = "id_teleservice";
    private static final String PARAMETER_LIEN_ID = "id_lien";

    // marks
    private static final String MARK_DONNEE_COMPLEMENTAIRE = "donneesComplementaires";
    private static final String MARK_TELESERVICE_LIST = "list_teleservices";
    private static final String MARK_LIEN_LIST = "list_liens";
    private static final String MARK_AUDIENCE = "audiences";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_LOCALE = "locale";

    // Beans
    private ComplementaryDataDTO _donneesComplementaires;
    private String _strAction;

    // Constants
    private int _nItemsPerPage;
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private IDilaComplementaryDataService _dilaComplementaryDataService = SpringContextService.getBean( 
            "dilaComplementaryDataService" );
    private IDilaComplementaryDataLinkService _dilaComplementaryDataLinkService = SpringContextService.getBean( 
            "dilaComplementaryDataLinkService" );
    private IDilaXmlService _dilaXmlService = SpringContextService.getBean( "dilaXmlService" );
    private IDilaAudienceService _dilaAudienceService = SpringContextService.getBean( "dilaAudienceService" );

    /**
     * Return management donnee complementaire
     * @param request The Http request
     * @return Html
     */
    public String getManageDonneeComplementaire( HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        String strId = request.getParameter( DilaConstants.MARK_ID );
        String strDonneesName = request.getParameter( DilaConstants.MARK_NAME );

        //Init fiche locale data table 
        List<ComplementaryDataDTO> listDonneesComplementaires = _dilaComplementaryDataService.findAll(  );
        List<ComplementaryDataDTO> listToDisplay = filtrer( listDonneesComplementaires, strDonneesName, strId );

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

        LocalizedPaginator<ComplementaryDataDTO> paginator = new LocalizedPaginator<ComplementaryDataDTO>( listToDisplay,
                _nItemsPerPage, strURL, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale(  ) );

        model.put( DilaConstants.MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( DilaConstants.MARK_PAGINATOR, paginator );
        model.put( DilaConstants.MARK_COMP_DATA_LIST, paginator.getPageItems(  ) );
        model.put( DilaConstants.MARK_ID, strId );
        model.put( DilaConstants.MARK_NAME, strDonneesName );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_COMPLEMENTARY_DATA, getLocale(  ),
                model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Return management fiche locale
     * @param request The Http request
     * @return Html
     */
    public String getCreateDonneeComplementaire( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.CREATE.getValue(  );

        Map<String, Object> model = new HashMap<String, Object>(  );

        _donneesComplementaires = new ComplementaryDataDTO(  );

        List<ComplementaryDataTeleserviceDTO> listTeleservice = new ArrayList<ComplementaryDataTeleserviceDTO>(  );
        List<ComplementaryDataLearnMoreDTO> listLien = new ArrayList<ComplementaryDataLearnMoreDTO>(  );

        model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
        model.put( MARK_TELESERVICE_LIST, listTeleservice );
        model.put( PARAMETER_TELESERVICE_ID, 0 );
        model.put( MARK_LIEN_LIST, listLien );
        model.put( PARAMETER_LIEN_ID, 0 );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_CREATE_DONNEE_COMPLEMENTAIRE,
                getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Return management fiche locale
     * @param request The Http request
     * @return Html
     */
    public String getModifyDonneeComplementaire( HttpServletRequest request )
    {
        _strAction = ActionTypeEnum.MODIFY.getValue(  );

        String idDonneeComplementaire = request.getParameter( DilaConstants.MARK_ID );

        if ( StringUtils.isEmpty( idDonneeComplementaire ) || !StringUtils.isNumeric( idDonneeComplementaire ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR, AdminMessage.TYPE_STOP );
        }

        _donneesComplementaires = _dilaComplementaryDataService.findById( Long.valueOf( idDonneeComplementaire ) );

        // get fiche infos
        verifyFiche( _donneesComplementaires.getCard(  ).getIdXml(  ) );

        if ( _donneesComplementaires == null )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR, AdminMessage.TYPE_STOP );
        }

        Map<String, Object> model = new HashMap<String, Object>(  );

        List<ComplementaryDataLinkDTO> listTeleservice = _dilaComplementaryDataLinkService.findByDataId( Long.valueOf( 
                    idDonneeComplementaire ), ComplementaryLinkTypeEnum.TELESERVICE );
        List<ComplementaryDataLinkDTO> listLien = _dilaComplementaryDataLinkService.findByDataId( Long.valueOf( 
                    idDonneeComplementaire ), ComplementaryLinkTypeEnum.LEARN_MORE );

        model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
        model.put( MARK_TELESERVICE_LIST, listTeleservice );
        model.put( PARAMETER_TELESERVICE_ID, listTeleservice.size(  ) );
        model.put( MARK_LIEN_LIST, listLien );
        model.put( PARAMETER_LIEN_ID, listLien.size(  ) );
        setDataInModel( request, model );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MODIFY_DONNEE_COMPLEMENTAIRE,
                getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
     * Returns the confirmation message to delete a DILA fiche locale
     * @param request The Http request
     * @return the html code message
     * @throws AccessDeniedException AccessDeniedException
     */
    public String getDeleteDonneeComplementaire( HttpServletRequest request )
        throws AccessDeniedException
    {
        String id = request.getParameter( DilaConstants.MARK_ID );

        if ( StringUtils.isEmpty( id ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR, AdminMessage.TYPE_STOP );
        }

        Map<String, Object> urlParam = new HashMap<String, Object>(  );
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
        Map<String, Object> model = new HashMap<String, Object>(  );

        populate( _donneesComplementaires, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );

        ComplementaryDataTeleserviceDTO teleservice = new ComplementaryDataTeleserviceDTO(  );
        teleservice.setPosition( nbTeleservice + 1 );
        teleservices.add( teleservice );

        model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
        model.put( PARAMETER_TELESERVICE_ID, nbTeleservice + 1 );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LIEN_ID, liens.size(  ) );
        model.put( MARK_LIEN_LIST, liens );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Delete a lien
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doDeleteTeleservice( HttpServletRequest request )
    {
        Integer nbTeleservice = Integer.valueOf( request.getParameter( PARAMETER_TELESERVICE_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );

        populate( _donneesComplementaires, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );
        teleservices.remove( teleservices.size(  ) - 1 );

        model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
        model.put( PARAMETER_TELESERVICE_ID, nbTeleservice - 1 );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LIEN_ID, liens.size(  ) );
        model.put( MARK_LIEN_LIST, liens );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Add a lien
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doAddLien( HttpServletRequest request )
    {
        Integer nbLien = Integer.valueOf( request.getParameter( PARAMETER_LIEN_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );

        populate( _donneesComplementaires, request );

        List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );
        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );

        ComplementaryDataLearnMoreDTO lien = new ComplementaryDataLearnMoreDTO(  );
        lien.setPosition( nbLien + 1 );
        liens.add( lien );

        model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
        model.put( PARAMETER_TELESERVICE_ID, teleservices.size(  ) );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LIEN_ID, nbLien + 1 );
        model.put( MARK_LIEN_LIST, liens );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Delete a lien
     * @param request the page request
     * @return the CreateDonneeComplementaire page
     */
    public String doDeleteEnSavoirPlus( HttpServletRequest request )
    {
        Integer nbLien = Integer.valueOf( request.getParameter( PARAMETER_LIEN_ID ) );
        Map<String, Object> model = new HashMap<String, Object>(  );

        populate( _donneesComplementaires, request );

        List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );
        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        liens.remove( liens.size(  ) - 1 );

        model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
        model.put( PARAMETER_TELESERVICE_ID, teleservices.size(  ) );
        model.put( MARK_TELESERVICE_LIST, teleservices );
        model.put( PARAMETER_LIEN_ID, nbLien - 1 );
        model.put( MARK_LIEN_LIST, liens );
        setDataInModel( request, model );

        return getAdminPage( getTemplate( model ) );
    }

    /**
     * Verify if a fiche exists
     * @param request the page request
     * @return the CreateDossierLocal page or an {@link AdminMessage}
     */
    public IPluginActionResult doVerifyFiche( HttpServletRequest request )
    {
        populate( _donneesComplementaires, request );

        IPluginActionResult result = new DefaultPluginActionResult(  );
        HashMap<String, Object> model = new HashMap<String, Object>(  );

        String errorKey = verifyFiche( _donneesComplementaires.getCard(  ).getIdXml(  ) );

        if ( errorKey != null )
        {
            // if error, we return the AdminMessage
            result.setRedirect( AdminMessageService.getMessageUrl( request, errorKey, AdminMessage.TYPE_STOP ) );
        }
        else
        {
            // Else, we display the Create Donnee page
            List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
            List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );

            model.put( MARK_DONNEE_COMPLEMENTAIRE, _donneesComplementaires );
            model.put( MARK_TELESERVICE_LIST, teleservices );
            model.put( PARAMETER_TELESERVICE_ID, teleservices.size(  ) );
            model.put( PARAMETER_LIEN_ID, liens.size(  ) );
            model.put( MARK_LIEN_LIST, liens );
            setDataInModel( request, model );

            HtmlTemplate templateList;
            templateList = AppTemplateService.getTemplate( TEMPLATE_CREATE_DONNEE_COMPLEMENTAIRE, getLocale(  ), model );
            result.setHtmlContent( getAdminPage( templateList.getHtml(  ) ) );
        }

        return result;
    }

    /**
     * Create a donnees complementaires
     * @param request the page request
     * @return the ManageFicheLocale page or an {@link AdminMessage}
     */
    public String doCreateDonneeComplementaire( HttpServletRequest request )
    {
        String result = null;
        populate( _donneesComplementaires, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );

        if ( StringUtils.isEmpty( _donneesComplementaires.getBottomBlock(  ) ) ||
                StringUtils.isEmpty( _donneesComplementaires.getColumnBlock(  ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        String errorKeyFiche = verifyFiche( _donneesComplementaires.getCard(  ).getIdXml(  ) );
        String errorKeyTeleservice = validateLiens( teleservices );
        String errorKeyLiens = validateLiens( liens );

        if ( StringUtils.isNotBlank( errorKeyFiche ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyFiche, AdminMessage.TYPE_STOP );
        }
        else if ( StringUtils.isNotBlank( errorKeyTeleservice ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyTeleservice, AdminMessage.TYPE_STOP );
        }
        else if ( StringUtils.isNotBlank( errorKeyLiens ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyLiens, AdminMessage.TYPE_STOP );
        }

        if ( result != null )
        {
            // in case of error, stop the treatment
            return result;
        }

        Long id = _dilaComplementaryDataService.create( _donneesComplementaires );

        for ( ComplementaryDataLinkDTO teleservice : teleservices )
        {
            teleservice.setIdComplementaryData( id );
            _dilaComplementaryDataLinkService.create( teleservice );
        }

        for ( ComplementaryDataLinkDTO lien : liens )
        {
            lien.setIdComplementaryData( id );
            _dilaComplementaryDataLinkService.create( lien );
        }

        return JSP_MANAGE_DONNEE_COMPLEMENTAIRE;
    }

    /**
     * Create a donnees complementaires
     * @param request the page request
     * @return the ManageFicheLocale page or an {@link AdminMessage}
     */
    public String doModifyDonneeComplementaire( HttpServletRequest request )
    {
        String result = null;

        populate( _donneesComplementaires, request );

        List<ComplementaryDataLinkDTO> teleservices = getExistingTeleservices( request );
        List<ComplementaryDataLinkDTO> liens = getExistingLiens( request );

        if ( StringUtils.isEmpty( _donneesComplementaires.getBottomBlock(  ) ) ||
                StringUtils.isEmpty( _donneesComplementaires.getColumnBlock(  ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        String errorKeyTeleservice = validateLiens( teleservices );
        String errorKeyLiens = validateLiens( liens );

        if ( StringUtils.isNotBlank( errorKeyTeleservice ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyTeleservice, AdminMessage.TYPE_STOP );
        }
        else if ( StringUtils.isNotBlank( errorKeyLiens ) )
        {
            result = AdminMessageService.getMessageUrl( request, errorKeyLiens, AdminMessage.TYPE_STOP );
        }

        if ( result != null )
        {
            // in case of error, stop the treatment
            return result;
        }

        _dilaComplementaryDataService.update( _donneesComplementaires );

        _dilaComplementaryDataLinkService.deleteFromComplementaryData( _donneesComplementaires.getId(  ),
            ComplementaryLinkTypeEnum.TELESERVICE );

        for ( ComplementaryDataLinkDTO teleservice : teleservices )
        {
            teleservice.setIdComplementaryData( _donneesComplementaires.getId(  ) );
            _dilaComplementaryDataLinkService.create( teleservice );
        }

        _dilaComplementaryDataLinkService.deleteFromComplementaryData( _donneesComplementaires.getId(  ),
            ComplementaryLinkTypeEnum.LEARN_MORE );

        for ( ComplementaryDataLinkDTO lien : liens )
        {
            lien.setIdComplementaryData( _donneesComplementaires.getId(  ) );
            _dilaComplementaryDataLinkService.create( lien );
        }

        return JSP_MANAGE_DONNEE_COMPLEMENTAIRE;
    }

    /**
     * Delete a DILA donnee complementaire.
     * @param request The Http request
     * @return url return
     * @throws AccessDeniedException AccessDeniedException
     */
    public String doDeleteDonneeComplementaire( HttpServletRequest request )
        throws AccessDeniedException
    {
        String id = request.getParameter( DilaConstants.MARK_ID );

        if ( StringUtils.isEmpty( id ) || !StringUtils.isNumeric( id ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR, AdminMessage.TYPE_STOP );
        }

        _dilaComplementaryDataLinkService.deleteFromComplementaryData( Long.valueOf( id ),
            ComplementaryLinkTypeEnum.TELESERVICE );
        _dilaComplementaryDataLinkService.deleteFromComplementaryData( Long.valueOf( id ),
            ComplementaryLinkTypeEnum.LEARN_MORE );
        _dilaComplementaryDataService.delete( Long.valueOf( id ) );

        return AppPathService.getBaseUrl( request ) + DilaConstants.JSP_MANAGE_DONNEES;
    }

    /**
     * Check validity of fiche id
     * @param id the id to check
     * @return the error key or null
     */
    private String verifyFiche( String id )
    {
        String result = null;

        if ( StringUtils.isBlank( _donneesComplementaires.getCard(  ).getIdXml(  ) ) ||
                ( _donneesComplementaires.getCard(  ).getIdXml(  ).length(  ) > 255 ) )
        {
            result = "dila.create_donnee.error.ficheFormat";
        }
        else if ( _dilaComplementaryDataService.cardHasComplement( _donneesComplementaires.getCard(  ).getIdXml(  ) ) )
        {
            result = "dila.create_donnee.error.ficheExistante";
        }
        else
        {
            XmlDTO ficheLien = null;

            if ( _donneesComplementaires.getCard(  ).getIdXml(  ).startsWith( "F" ) )
            {
                // Search in XML folder
                List<String> availableTypes = new ArrayList<String>(  );
                availableTypes.add( ResourceTypeEnum.CARD.getLabel(  ) );

                ficheLien = _dilaXmlService.findByIdAndTypesAndAudience( _donneesComplementaires.getCard(  ).getIdXml(  ),
                        availableTypes, _donneesComplementaires.getIdAudience(  ) );
            }

            if ( ficheLien == null )
            {
                result = "dila.create_donnee.error.ficheIntrouvable";
            }
            else
            {
                _donneesComplementaires.setCard( ficheLien );
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
        List<ComplementaryDataLinkDTO> teleservices = new ArrayList<ComplementaryDataLinkDTO>(  );

        for ( int i = 1; i <= nbTeleservice; i++ )
        {
            String titre = request.getParameter( "teleservice_title_" + i );
            String url = request.getParameter( "teleservice_url_" + i );

            ComplementaryDataTeleserviceDTO teleservice = new ComplementaryDataTeleserviceDTO(  );
            teleservice.setTitle( titre );
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
    private List<ComplementaryDataLinkDTO> getExistingLiens( HttpServletRequest request )
    {
        Integer nbLien = Integer.valueOf( request.getParameter( PARAMETER_LIEN_ID ) );
        List<ComplementaryDataLinkDTO> liens = new ArrayList<ComplementaryDataLinkDTO>(  );

        for ( int i = 1; i <= nbLien; i++ )
        {
            String titre = request.getParameter( "lien_title_" + i );
            String url = request.getParameter( "lien_url_" + i );

            ComplementaryDataLearnMoreDTO lien = new ComplementaryDataLearnMoreDTO(  );
            lien.setTitle( titre );
            lien.setURL( url );
            lien.setPosition( i );

            liens.add( lien );
        }

        return liens;
    }

    /**
     * Check the list of liens
     * @param liens list to check
     * @return error key or null
     */
    private String validateLiens( List<ComplementaryDataLinkDTO> liens )
    {
        String result = null;

        for ( ComplementaryDataLinkDTO lien : liens )
        {
            if ( StringUtils.isBlank( lien.getTitle(  ) ) )
            {
                if ( lien.getType(  ).equals( ComplementaryLinkTypeEnum.TELESERVICE ) )
                {
                    result = "dila.create_donnee.error.teleserviceTitre";
                }
                else
                {
                    result = "dila.create_donnee.error.lienTitre";
                }

                break;
            }
            else if ( StringUtils.isBlank( lien.getURL(  ) ) )
            {
                if ( lien.getType(  ).equals( ComplementaryLinkTypeEnum.TELESERVICE ) )
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
     * @param listDonneesComplementaires original list
     * @param strDonneesName fiche title
     * @param strId fiche id
     * @return filtered list
     */
    private List<ComplementaryDataDTO> filtrer( List<ComplementaryDataDTO> listDonneesComplementaires,
        String strDonneesName, String strId )
    {
        List<ComplementaryDataDTO> result = new ArrayList<ComplementaryDataDTO>(  );

        for ( ComplementaryDataDTO donnee : listDonneesComplementaires )
        {
            if ( StringUtils.isNotBlank( strDonneesName ) && !donnee.getCard(  ).getTitle(  ).equals( strDonneesName ) )
            {
                continue;
            }

            if ( StringUtils.isNotBlank( strId ) && !donnee.getCard(  ).getIdXml(  ).equals( strId ) )
            {
                continue;
            }

            result.add( donnee );
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
        List<AudienceDTO> audienceList = _dilaAudienceService.findAll(  );
        ReferenceList listTypeContenu = ListUtils.toReferenceList( audienceList, "id", "label", null );
        model.put( MARK_AUDIENCE, listTypeContenu );
        model.put( MARK_LOCALE, getLocale(  ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
    }

    /**
     * Get the current template
     * @param model the template model
     * @return the current html template
     */
    private String getTemplate( Map<String, Object> model )
    {
        if ( ActionTypeEnum.CREATE.getValue(  ).equals( _strAction ) )
        {
            return AppTemplateService.getTemplate( TEMPLATE_CREATE_DONNEE_COMPLEMENTAIRE, getLocale(  ), model )
                                     .getHtml(  );
        }
        else
        {
            return AppTemplateService.getTemplate( TEMPLATE_MODIFY_DONNEE_COMPLEMENTAIRE, getLocale(  ), model )
                                     .getHtml(  );
        }
    }
}
