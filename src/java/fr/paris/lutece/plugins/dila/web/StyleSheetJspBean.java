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

import fr.paris.lutece.plugins.dila.business.stylesheet.DilaStyleSheetAction;
import fr.paris.lutece.plugins.dila.business.stylesheet.dto.ContentType;
import fr.paris.lutece.plugins.dila.business.stylesheet.dto.DilaStyleSheet;
import fr.paris.lutece.plugins.dila.service.DilaStyleSheetResourceIdService;
import fr.paris.lutece.plugins.dila.service.IContentTypeService;
import fr.paris.lutece.plugins.dila.service.IDilaStyleSheetActionService;
import fr.paris.lutece.plugins.dila.service.IDilaStyleSheetService;
import fr.paris.lutece.plugins.dila.utils.ListUtils;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.fileupload.FileUploadService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.filesystem.FileSystemUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.sort.AttributeComparator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * This class provides the user interface to manage StyleSheet of DILA features
 */
public class StyleSheetJspBean extends PluginAdminPageJspBean
{
    // Right
    public static final String RIGHT_MANAGE_STYLESHEET = "DILA_STYLESHEET_MANAGEMENT";

    /** The serial ID */
    private static final long serialVersionUID = 2933032349706505918L;

    // Constants
    private int _nItemsPerPage;
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;

    // Services
    private IContentTypeService _typeContenuService = SpringContextService.getBean( "contentTypeService" );
    private IDilaStyleSheetService _dilaStyleSheetService = SpringContextService.getBean( "dilaStyleSheetService" );
    private IDilaStyleSheetActionService _dilaStyleSheetActionService = SpringContextService
            .getBean( "dilaStyleSheetActionService" );

    /**
     * Displays the stylesheets list
     * @return the html code for displaying the stylesheets list
     * @param request The request
     */
    public String getManageStyleSheet( HttpServletRequest request )
    {
        // Parameters processing
        String strContentTypeId = request.getParameter( DilaConstants.MARK_CONTENT_TYPE_ID );
        strContentTypeId = ( strContentTypeId != null ) ? strContentTypeId : "0";

        String strStyleSheetName = request.getParameter( DilaConstants.MARK_NAME );

        int nContentTypeId = Integer.parseInt( strContentTypeId );

        // Get the list of ContentType
        ReferenceList contentTypeList = ListUtils.toReferenceList( _typeContenuService.getContentTypes( ), "id",
                "label", I18nService.getLocalizedString( DilaConstants.LABEL_WITHOUT_TYPE, getLocale( ) ) );
        ReferenceItem itemAll = new ReferenceItem( );
        itemAll.setCode( "0" );
        itemAll.setName( I18nService.getLocalizedString( DilaConstants.LABEL_ALL, getLocale( ) ) );
        contentTypeList.add( itemAll );

        // Get the list of style sheet with filters
        List<DilaStyleSheet> listStyleSheets = _dilaStyleSheetService.getDilaStyleSheetList( nContentTypeId,
                strStyleSheetName );

        // Orders the list of result and add pagination
        String strSortedAttributeName = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strAscSort = null;

        if ( strSortedAttributeName != null )
        {
            strAscSort = request.getParameter( Parameters.SORTED_ASC );

            boolean bIsAscSort = Boolean.parseBoolean( strAscSort );

            Collections.sort( listStyleSheets, new AttributeComparator( strSortedAttributeName, bIsAscSort ) );
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

        LocalizedPaginator<DilaStyleSheet> paginator = new LocalizedPaginator<DilaStyleSheet>( listStyleSheets,
                _nItemsPerPage, strURL, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale( ) );

        // Permissions
        Map<String, Object> model = new HashMap<String, Object>( );

        if ( RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                DilaStyleSheetResourceIdService.PERMISSION_CREATE_STYLESHEET, getUser( ) ) )
        {
            model.put( DilaConstants.MARK_PERMISSION_CREATE_STYLESHEET, true );
        }
        else
        {
            model.put( DilaConstants.MARK_PERMISSION_CREATE_STYLESHEET, false );
        }

        List<DilaStyleSheetAction> listActions;
        listActions = _dilaStyleSheetActionService.selectActions( getLocale( ) );

        List<DilaStyleSheetAction> listActionsForStyleSheet;

        for ( DilaStyleSheet styleSheet : paginator.getPageItems( ) )
        {
            listActionsForStyleSheet = (List<DilaStyleSheetAction>) RBACService.getAuthorizedActionsCollection(
                    listActions, styleSheet, getUser( ) );
            styleSheet.setListActions( listActionsForStyleSheet );
        }

        // Get the html with freemarker
        model.put( DilaConstants.MARK_CONTENT_TYPE_ID, strContentTypeId );
        model.put( DilaConstants.MARK_NAME, strStyleSheetName );
        model.put( DilaConstants.MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( DilaConstants.MARK_PAGINATOR, paginator );
        model.put( DilaConstants.MARK_STYLESHEET_LIST, paginator.getPageItems( ) );
        model.put( DilaConstants.MARK_CONTENT_TYPE_LIST, contentTypeList );
        model.put( DilaConstants.MARK_PERMISSION_MODIFY_STYLESHEET,
                DilaStyleSheetResourceIdService.PERMISSION_MODIFY_STYLESHEET );
        model.put( DilaConstants.MARK_PERMISSION_DELETE_STYLESHEET,
                DilaStyleSheetResourceIdService.PERMISSION_DELETE_STYLESHEET );
        model.put( DilaConstants.MARK_PERMISSION_VIEW_STYLESHEET,
                DilaStyleSheetResourceIdService.PERMISSION_VIEW_STYLESHEET );

        HtmlTemplate template = AppTemplateService.getTemplate( DilaConstants.TEMPLATE_MANAGE_STYLESHEETS,
                getLocale( ), model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Returns the create form of a new dila stylesheet with the upload field
     * @param request the http request
     * @return the html code for the create form of a new stylesheet
     * @throws AccessDeniedException AccessDeniedException
     */
    public String getSaveStyleSheet( HttpServletRequest request ) throws AccessDeniedException
    {
        Map<String, Object> model = new HashMap<String, Object>( );

        String strStyleSheetId = request.getParameter( DilaConstants.MARK_STYLESHEET_ID );

        // Modification
        int nIdStylesheet = 0;

        if ( StringUtils.isNotEmpty( strStyleSheetId ) )
        {
            if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, strStyleSheetId,
                    DilaStyleSheetResourceIdService.PERMISSION_MODIFY_STYLESHEET, getUser( ) ) )
            {
                return getManageStyleSheet( request );
            }

            try
            {
                nIdStylesheet = Integer.parseInt( strStyleSheetId );
            }
            catch ( NumberFormatException e )
            {
                nIdStylesheet = -1;
            }

            if ( nIdStylesheet > 0 )
            {
                DilaStyleSheet styleSheet = _dilaStyleSheetService.findByPrimaryKey( nIdStylesheet );
                styleSheet.setListActions( (List<DilaStyleSheetAction>) RBACService.getAuthorizedActionsCollection(
                        _dilaStyleSheetActionService.selectActions( getLocale( ) ), styleSheet, getUser( ) ) );
                model.put( DilaConstants.MARK_STYLESHEET, styleSheet );
                model.put( DilaConstants.MARK_PERMISSION_VIEW_STYLESHEET,
                        DilaStyleSheetResourceIdService.PERMISSION_VIEW_STYLESHEET );
            }
        }
        else if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                DilaStyleSheetResourceIdService.PERMISSION_CREATE_STYLESHEET, getUser( ) ) )
        {
            return getManageStyleSheet( request );
        }

        //Getting content types
        ReferenceList contentTypeList = ListUtils.toReferenceList(
                _typeContenuService.getContentTypesWithoutAssociatedStyleSheet( nIdStylesheet ), "id", "label",
                I18nService.getLocalizedString( DilaConstants.LABEL_WITHOUT_TYPE, getLocale( ) ) );
        model.put( DilaConstants.MARK_CONTENT_TYPE_LIST, contentTypeList );

        HtmlTemplate template = AppTemplateService.getTemplate( DilaConstants.TEMPLATE_CREATE_STYLESHEET, getLocale( ),
                model );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Processes the creation form of a new dila stylesheet by recovering the
     * parameters
     * in the http request
     * @param request the http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException AccessDeniedException
     */
    public String doSaveStyleSheet( HttpServletRequest request ) throws AccessDeniedException
    {
        DilaStyleSheet stylesheet = new DilaStyleSheet( );

        if ( !( request instanceof MultipartHttpServletRequest ) )
        {
            throw new AssertionError( "Unexpected type: " + request );
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // Modification
        String strIdStylesheet = multipartRequest.getParameter( DilaConstants.MARK_STYLESHEET_ID );

        if ( StringUtils.isNotEmpty( strIdStylesheet ) )
        {
            if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, strIdStylesheet,
                    DilaStyleSheetResourceIdService.PERMISSION_MODIFY_STYLESHEET, getUser( ) ) )
            {
                throw new AccessDeniedException( "User is not authrorized to access this page." );
            }

            Integer nId;

            try
            {
                nId = Integer.parseInt( strIdStylesheet );
            }
            catch ( NumberFormatException e )
            {
                nId = -1;
            }

            if ( nId > 0 )
            {
                stylesheet.setId( nId );
            }
        }
        else if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                DilaStyleSheetResourceIdService.PERMISSION_CREATE_STYLESHEET, getUser( ) ) )
        {
            throw new AccessDeniedException( "User is not authrorized to access this page." );
        }

        // creation or modification
        boolean bIsModification = ( stylesheet.getId( ) > 0 ) ? true : false;

        String strErrorUrl = getData( multipartRequest, stylesheet, bIsModification );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }
        else if ( bIsModification )
        {
            // Remove the old local file if change
            if ( stylesheet.getSource( ) != null )
            {
                removeOldLocalStyleSheet( stylesheet.getId( ) );
            }

            // Update the stylesheet in database
            _dilaStyleSheetService.update( stylesheet );
        }
        else
        {
            //insert in the table stylesheet of the database
            _dilaStyleSheetService.create( stylesheet );
        }

        //create a local file
        if ( stylesheet.getSource( ) != null )
        {
            localStyleSheetFile( stylesheet );
        }

        //Displays the list of the stylesheet files
        return getHomeUrl( request );
    }

    /**
     * Returns the confirmation message to delete a dila stylesheet.
     * 
     * @param request The Http request
     * @return the html code message
     * @throws AccessDeniedException AccessDeniedException
     */
    public String getDeleteStyleSheet( HttpServletRequest request ) throws AccessDeniedException
    {
        String strStylesheetId = request.getParameter( DilaConstants.MARK_STYLESHEET_ID );

        if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, strStylesheetId,
                DilaStyleSheetResourceIdService.PERMISSION_DELETE_STYLESHEET, getUser( ) ) )
        {
            throw new AccessDeniedException( "User is not authrorized to access this page." );
        }

        int nIdStylesheet = 0;

        try
        {
            nIdStylesheet = Integer.parseInt( strStylesheetId );
        }
        catch ( NumberFormatException e )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        Map<String, Object> urlParam = new HashMap<String, Object>( );
        urlParam.put( DilaConstants.MARK_STYLESHEET_ID, nIdStylesheet );

        return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_CONFIRMATION_DELETE_STYLESHEET, null,
                DilaConstants.MESSAGE_TITLE_DELETE_STYLESHEET, DilaConstants.JSP_DELETE_STYLESHEET, "_self",
                AdminMessage.TYPE_CONFIRMATION, urlParam, DilaConstants.JSP_MANAGE_STYLESHEET );
    }

    /**
     * Delete a dila styleSheet.
     * 
     * @param request The Http request
     * @return url return
     * @throws AccessDeniedException AccessDeniedException
     */
    public String doDeleteStyleSheet( HttpServletRequest request ) throws AccessDeniedException
    {
        String strStyleSheetId = request.getParameter( DilaConstants.MARK_STYLESHEET_ID );

        if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, strStyleSheetId,
                DilaStyleSheetResourceIdService.PERMISSION_DELETE_STYLESHEET, getUser( ) ) )
        {
            throw new AccessDeniedException( "User is not authrorized to access this page." );
        }

        if ( !StringUtils.isNumeric( strStyleSheetId ) )
        {
            return AdminMessageService.getMessageUrl( request, DilaConstants.MESSAGE_ERROR_OCCUR,
                    AdminMessage.TYPE_STOP );
        }

        Integer nIdStyleSheet = Integer.parseInt( strStyleSheetId );
        removeOldLocalStyleSheet( nIdStyleSheet );
        _dilaStyleSheetService.doDeleteStyleSheet( nIdStyleSheet );

        return AppPathService.getBaseUrl( request ) + DilaConstants.JSP_MANAGE_STYLESHEET;
    }

    /**
     * Reads dila stylesheet's data
     * @param multipartRequest The request
     * @param stylesheet The style sheet
     * @param bIsModification If is modification
     * @return An error message URL or null if no error
     */
    private String getData( MultipartHttpServletRequest multipartRequest, DilaStyleSheet stylesheet,
            boolean bIsModification )
    {
        String strErrorUrl = null;
        String strName = multipartRequest.getParameter( DilaConstants.MARK_NAME );
        String strContentTypeId = multipartRequest.getParameter( DilaConstants.MARK_CONTENT_TYPE_ID );

        FileItem fileSource = multipartRequest.getFile( DilaConstants.MARK_SOURCE );
        byte[] baXslSource = fileSource.get( );
        String strFilename = FileUploadService.getFileNameOnly( fileSource );

        boolean modification = StringUtils
                .isNotEmpty( multipartRequest.getParameter( DilaConstants.MARK_STYLESHEET_ID ) );

        // Mandatory fields
        boolean isMandatoryOk = true;

        if ( strName.equals( "" ) || ( strFilename == null ) || strFilename.equals( "" ) )
        {
            if ( !modification )
            {
                isMandatoryOk = false;
            }
        }

        if ( baXslSource == null )
        {
            isMandatoryOk = false;
        }

        if ( !isMandatoryOk )
        {
            return AdminMessageService.getMessageUrl( multipartRequest, DilaConstants.MESSAGE_MANDATORY_FIELD,
                    AdminMessage.TYPE_STOP );
        }

        //test the existence of content type already associated with this stylesheet except if there's no type
        int nContentTypeId = Integer.parseInt( strContentTypeId );

        if ( nContentTypeId > 0 )
        {
            int nCount = _dilaStyleSheetService.getStyleSheetNbPerTypeContenu( nContentTypeId );

            // Do not create a stylesheet of there is already one
            DilaStyleSheet oldStylesheet = _dilaStyleSheetService.findByPrimaryKey( stylesheet.getId( ) );

            if ( ( ( nCount >= 1 ) && ( stylesheet.getId( ) == 0 ) )
                    || ( ( oldStylesheet != null ) && ( ( oldStylesheet.getContentType( ).getId( ) != nContentTypeId ) && ( nCount >= 1 ) ) ) )
            {
                return AdminMessageService.getMessageUrl( multipartRequest,
                        DilaConstants.MESSAGE_STYLESHEET_ALREADY_EXISTS, AdminMessage.TYPE_STOP );
            }
        }

        // Check the XML validity of the XSL stylesheet
        String isValid = isValid( baXslSource );

        if ( isValid != null )
        {
            Object[] args = { isValid };

            return AdminMessageService.getMessageUrl( multipartRequest, DilaConstants.MESSAGE_STYLESHEET_NOT_VALID,
                    args, AdminMessage.TYPE_STOP );
        }

        stylesheet.setDescription( strName );

        ContentType contentType = new ContentType( );
        contentType.setId( nContentTypeId );
        stylesheet.setContentType( contentType );
        stylesheet.setSource( baXslSource );

        // get existing stylesheet
        DilaStyleSheet dilaStyleSheet = null;

        if ( bIsModification )
        {
            dilaStyleSheet = _dilaStyleSheetService.findByPrimaryKey( stylesheet.getId( ) );
        }

        boolean isNewFileModification = ( dilaStyleSheet != null )
                && !strFilename.equalsIgnoreCase( dilaStyleSheet.getFile( ) );

        // creation : test if file already exists
        // modification : if new file, test if file already exists
        if ( !bIsModification || isNewFileModification )
        {
            File file = new File( AppPathService.getPath( DilaConstants.PROPERTY_PATH_XSL ) + strFilename );

            if ( file.exists( ) && file.isFile( ) )
            {
                return AdminMessageService.getMessageUrl( multipartRequest,
                        DilaConstants.MESSAGE_STYLESHEET_NAME_EXISTS, AdminMessage.TYPE_STOP );
            }
        }

        stylesheet.setFile( strFilename );

        return strErrorUrl;
    }

    /**
     * Use parsing for validate the modify xsl file
     * 
     * @param baXslSource The XSL source
     * @return the message exception when the validation is false
     */
    private String isValid( byte[] baXslSource )
    {
        String strError = null;

        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance( );
            SAXParser analyzer = factory.newSAXParser( );
            InputSource is = new InputSource( new ByteArrayInputStream( baXslSource ) );
            analyzer.getXMLReader( ).parse( is );
        }
        catch ( SAXException e )
        {
            strError = e.getMessage( );
        }
        catch ( ParserConfigurationException e )
        {
            strError = e.getMessage( );
        }
        catch ( IOException e )
        {
            strError = e.getMessage( );
        }

        return strError;
    }

    /**
     * Create and Update the local download file
     * 
     * @param stylesheet The dila style sheet
     */
    private void localStyleSheetFile( DilaStyleSheet stylesheet )
    {
        String strPathStyleSheet = AppPathService.getPath( DilaConstants.PROPERTY_PATH_XSL ) + stylesheet.getFile( );
        FileOutputStream fos = null;

        try
        {
            File file = new File( strPathStyleSheet );

            if ( file.exists( ) )
            {
                if ( !file.delete( ) )
                {
                    AppLogService.error( "Erreur lors de la suppression du fichier" );
                }
            }

            fos = new FileOutputStream( file );
            fos.write( stylesheet.getSource( ) );
            fos.flush( );
        }
        catch ( IOException e )
        {
            AppLogService.error( e.getMessage( ), e );
        }
        finally
        {
            IOUtils.closeQuietly( fos );
        }
    }

    /**
     * remove the xsl file from the tmp directory
     * @param nId the identifier of the file
     */
    private void removeOldLocalStyleSheet( int nId )
    {
        //Remove the file which been modify
        DilaStyleSheet stylesheet = _dilaStyleSheetService.findByPrimaryKey( nId );
        String strPathStyleSheet = AppPathService.getPath( DilaConstants.PROPERTY_PATH_XSL );
        String strOldFileName = stylesheet.getFile( );
        String strOldFilePath = strPathStyleSheet + strOldFileName;
        File oldFile = new File( strOldFilePath );

        if ( oldFile.exists( ) )
        {
            if ( !oldFile.delete( ) )
            {
                AppLogService.error( "Erreur lors de la suppression du fichier" );
            }
        }
    }

    /**
     * write in the http response the value of the response whose identifier is
     * specified in the request if there is no response return a error
     * @param request the http request
     * @param response The http response
     * @return The URL to go after performing the action
     * @throws AccessDeniedException AccessDeniedException
     */
    public String doDownloadStyleSheet( HttpServletRequest request, HttpServletResponse response )
            throws AccessDeniedException
    {
        String strIdStyleSheet = request.getParameter( DilaConstants.MARK_STYLESHEET_ID );

        if ( !RBACService.isAuthorized( DilaStyleSheet.RESOURCE_TYPE, strIdStyleSheet,
                DilaStyleSheetResourceIdService.PERMISSION_VIEW_STYLESHEET, getUser( ) ) )
        {
            throw new AccessDeniedException( "User is not authrorized to access this page." );
        }

        int nIdStyleSheet = -1;

        if ( strIdStyleSheet != null )
        {
            try
            {
                nIdStyleSheet = Integer.parseInt( strIdStyleSheet );
            }
            catch ( NumberFormatException ne )
            {
                AppLogService.error( ne );
            }
        }

        DilaStyleSheet stylesheet = _dilaStyleSheetService.findByPrimaryKey( nIdStyleSheet );

        response.setHeader( "Content-Disposition", "attachment ;filename=\"" + stylesheet.getFile( ) + "\"" );

        response.setCharacterEncoding( "UTF-8" );

        String strMimeType = FileSystemUtil.getMIMEType( stylesheet.getFile( ) );

        if ( strMimeType != null )
        {
            response.setContentType( strMimeType );
        }
        else
        {
            response.setContentType( "application/octet-stream" );
        }

        response.setHeader( "Pragma", "public" );
        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate,post-check=0,pre-check=0" );

        try
        {
            byte[] byteFileOutPut = _dilaStyleSheetService.getSourceByStyleSheetId( nIdStyleSheet );

            if ( byteFileOutPut != null )
            {
                response.setContentLength( byteFileOutPut.length );

                OutputStream os = response.getOutputStream( );
                os.write( byteFileOutPut );
                os.close( );
            }
        }
        catch ( IOException e )
        {
            AppLogService.error( e );
        }

        return AppPathService.getBaseUrl( request ) + DilaConstants.JSP_MANAGE_STYLESHEET;
    }
}
