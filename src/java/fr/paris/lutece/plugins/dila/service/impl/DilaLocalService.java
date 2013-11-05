package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Implementation of {@link IDilaLocalService}
 */
public class DilaLocalService implements IDilaLocalService, Serializable
{
    private static final String ID_ATTRIBUTE = "ID";

    private static final String CARD_TAG = "Fiche";

    /** The serial ID */
    private static final long serialVersionUID = -9211044575927578839L;

    @Inject
    @Named( "dilaLocalDAO" )
    private ILocalDAO _dilaLocalDAO;

    @Override
    public LocalDTO findLocalByIdAndTypeAndAudience( Long id, Long type, Long idAudience )
    {
        return _dilaLocalDAO.findLocalByIdAndTypeAndAudience( id, type, idAudience );
    }

    @Override
    public String findTitleByIdAndTypeAndAudience( Long id, Long type, Long idAudience )
    {
        return _dilaLocalDAO.findTitleByIdAndTypeAndAudience( id, type, idAudience );
    }

    @Override
    public List<LocalDTO> findAll( )
    {
        return _dilaLocalDAO.findAll( );
    }

    @Override
    public Long create( LocalDTO local, boolean addIdToBreadcrumb )
    {
        return _dilaLocalDAO.insert( local, addIdToBreadcrumb );
    }

    @Override
    public void delete( String idLocal )
    {
        _dilaLocalDAO.delete( idLocal );

    }

    @Override
    public void update( LocalDTO localDTO, boolean addIdToBreadcrumb )
    {
        _dilaLocalDAO.store( localDTO, addIdToBreadcrumb );
    }

    @Override
    public String findXmlById( Long id )
    {
        return _dilaLocalDAO.findXmlById( id );
    }

    @Override
    public Document insertLastCardsLinks( Long categoryId, DocumentBuilder builder, Document document )
    {
        List<LocalDTO> localList = this.findLastCardsByAudience( categoryId );
        for ( LocalDTO currentLocal : localList )
        {
            Element newCard = document.createElement( CARD_TAG );
            newCard.setAttribute( ID_ATTRIBUTE, currentLocal.getId( ).toString( ) );
            newCard.setTextContent( currentLocal.getTitle( ) );
            document.getDocumentElement( ).appendChild( newCard );
            document.getDocumentElement( ).normalize( );
        }

        return document;
    }

    /**
     * Return last locale cards by audience
     * @param lAudienceId the audience to check
     * @return the list of cards
     */
    private List<LocalDTO> findLastCardsByAudience( Long lAudienceId )
    {
        return _dilaLocalDAO.findLastCardsByAudience( lAudienceId );
    }

    @Override
    public List<LocalDTO> findAllByAudienceId( Long audienceId )
    {
        return _dilaLocalDAO.findAllByAudienceId( audienceId );
    }
}
