/**
 * 
 */
package fr.paris.lutece.plugins.dila.web;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.plugins.dila.utils.constants.DilaConstants;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.WordUtils;


/**
 * Dila page for local cards
 * 
 */
public class DilaLocalApp implements XPageApplication
{

    private static final String PROPERTY_ERROR_NOCATEGORY = "dila.page.dilalocal.error.nocategory";
    // Parameters
    private static final String DEFAULT_RESULTS_PER_PAGE = "20";
    private static final String DEFAULT_PAGE_INDEX = "1";
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    private static final String PARAMETER_NB_ITEMS_PER_PAGE = "items_per_page";
    private static final String PROPERTY_SEARCH_PAGE_URL = "jsp/site/Portal.jsp?page=dilaLocal";

    // Markers
    private static final String MARK_CATEGORIE = "categorie";
    private static final String MARK_ERROR = "error";
    private static final String MARK_RESULTS_LIST = "results_list";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_URL_INDIVIDUALS = "urlParticuliers";
    private static final String MARK_URL_PROFESSIONALS = "urlPME";
    private static final String MARK_URL_ASSOCIATIONS = "urlAssociations";

    private static final String TEMPLATE_RESULTS = "skin/plugins/dila/page_dila_locale.html";
    private static final String PROPERTY_PATH_LABEL = "portal.search.search_results.pathLabel";
    private static final String PROPERTY_PAGE_TITLE = "portal.search.search_results.pageTitle";

    private IDilaLocalService _dilaLocalService = SpringContextService.getBean( "dilaLocalService" );

    /**
     * {@inheritDoc}
     */
    @Override
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin ) throws UserNotSignedException,
            SiteMessageException
    {
        XPage page = new XPage( );
        HashMap<String, Object> model = new HashMap<String, Object>( );
        String strAudience = request.getParameter( MARK_CATEGORIE );
        model.put( MARK_URL_ASSOCIATIONS, DilaConstants.XPAGE_ASSO );
        model.put( MARK_URL_INDIVIDUALS, DilaConstants.XPAGE_PARTICULIERS );
        model.put( MARK_URL_PROFESSIONALS, DilaConstants.XPAGE_PME );
        if ( strAudience == null )
        {
            model.put( MARK_ERROR, I18nService.getLocalizedString( PROPERTY_ERROR_NOCATEGORY, request.getLocale( ) ) );
        }
        else
        {
            model.put( MARK_CATEGORIE, WordUtils.capitalize( strAudience ) );
            String strSearchPageUrl = PROPERTY_SEARCH_PAGE_URL;

            String strNbItemPerPage = request.getParameter( PARAMETER_NB_ITEMS_PER_PAGE );
            strNbItemPerPage = ( strNbItemPerPage != null ) ? strNbItemPerPage : DEFAULT_RESULTS_PER_PAGE;

            int nNbItemsPerPage = Integer.parseInt( strNbItemPerPage );
            String strCurrentPageIndex = request.getParameter( PARAMETER_PAGE_INDEX );
            strCurrentPageIndex = ( strCurrentPageIndex != null ) ? strCurrentPageIndex : DEFAULT_PAGE_INDEX;

            AudienceCategoryEnum audienceEnum = AudienceCategoryEnum.fromLabel( strAudience );
            List<LocalDTO> listResults = new ArrayList<LocalDTO>( );
            if ( audienceEnum != null )
            {
                listResults = _dilaLocalService.findAllByAudienceId( audienceEnum.getId( ) );
            }

            UrlItem url = new UrlItem( strSearchPageUrl );
            url.addParameter( MARK_CATEGORIE, strAudience );
            url.addParameter( PARAMETER_NB_ITEMS_PER_PAGE, nNbItemsPerPage );

            Paginator<LocalDTO> paginator = new Paginator<LocalDTO>( listResults, nNbItemsPerPage, url.getUrl( ),
                    PARAMETER_PAGE_INDEX, strCurrentPageIndex );
            model.put( MARK_RESULTS_LIST, paginator.getPageItems( ) );
            model.put( MARK_PAGINATOR, paginator );
            model.put( MARK_NB_ITEMS_PER_PAGE, strNbItemPerPage );

        }
        Locale locale = request.getLocale( );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_RESULTS, locale, model );

        page.setPathLabel( I18nService.getLocalizedString( PROPERTY_PATH_LABEL, locale ) );
        page.setTitle( I18nService.getLocalizedString( PROPERTY_PAGE_TITLE, locale ) );
        page.setContent( template.getHtml( ) );
        return page;
    }
}
