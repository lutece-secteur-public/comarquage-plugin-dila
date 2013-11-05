package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dao.IComplementaryDataDAO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataDTO;
import fr.paris.lutece.plugins.dila.business.donneescomplementaires.dto.ComplementaryDataLinkDTO;
import fr.paris.lutece.plugins.dila.business.enums.ComplementaryLinkTypeEnum;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataLinkService;
import fr.paris.lutece.plugins.dila.service.IDilaComplementaryDataService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Implementation of {@link IDilaComplementaryDataService}
 */
public class DilaComplementaryDataService implements IDilaComplementaryDataService, Serializable
{
    private static final String TYPE_ATTRIBUTE = "type";

    private static final String PROPERTY_TELESERVICE = "Téléservice";

    private static final String TELESERVICE_TAG = "ServiceEnLigne";

    private static final String TITLE_TAG = "Titre";

    private static final String SEQ_ATTRIBUTE = "seq";

    private static final String URL_ATTRIBUTE = "URL";

    private static final String LEARN_MORE_TAG = "PourEnSavoirPlus";

    private static final String COLUMN_BLOCK_TAG = "BlocColonne";

    private static final String BOTTOM_BLOCK_TAG = "BlocBas";

    /** The serial ID */
    private static final long serialVersionUID = 353073235359709773L;

    @Inject
    @Named( "dilaComplementaryDataDAO" )
    private IComplementaryDataDAO _dilaComplementaryDataDAO;

    @Inject
    @Named( "dilaComplementaryDataLinkService" )
    private IDilaComplementaryDataLinkService _dilaComplementaryDataLinkService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ComplementaryDataDTO> findAll( )
    {
        return _dilaComplementaryDataDAO.findAll( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long create( ComplementaryDataDTO dto )
    {
        return _dilaComplementaryDataDAO.insert( dto );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cardHasComplement( String id )
    {
        return _dilaComplementaryDataDAO.cardHasComplement( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComplementaryDataDTO findById( Long id )
    {
        return _dilaComplementaryDataDAO.findById( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( ComplementaryDataDTO complementaryData )
    {
        _dilaComplementaryDataDAO.store( complementaryData );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( Long id )
    {
        _dilaComplementaryDataDAO.delete( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document insertComplementaryData( Long cardId, Long categoryId, DocumentBuilder builder, Document document )
    {
        ComplementaryDataDTO complementaryDataDTO = this.findByCardAndAudience( cardId, categoryId );
        if ( complementaryDataDTO != null )
        {
            if ( StringUtils.isNotBlank( complementaryDataDTO.getBottomBlock( ) ) )
            {
                Element bottomBlock = document.createElement( BOTTOM_BLOCK_TAG );
                bottomBlock.setTextContent( complementaryDataDTO.getBottomBlock( ) );
                document.getDocumentElement( ).appendChild( bottomBlock );
                document.getDocumentElement( ).normalize( );
            }
            if ( StringUtils.isNotBlank( complementaryDataDTO.getColumnBlock( ) ) )
            {
                Element columnBlock = document.createElement( COLUMN_BLOCK_TAG );
                columnBlock.setTextContent( complementaryDataDTO.getColumnBlock( ) );
                document.getDocumentElement( ).appendChild( columnBlock );
                document.getDocumentElement( ).normalize( );
            }
            List<ComplementaryDataLinkDTO> listLearnMoreLinks = _dilaComplementaryDataLinkService.findByDataId(
                    complementaryDataDTO.getId( ), ComplementaryLinkTypeEnum.LEARN_MORE );
            if ( CollectionUtils.isNotEmpty( listLearnMoreLinks ) )
            {
                for ( ComplementaryDataLinkDTO currentLink : listLearnMoreLinks )
                {
                    Element currentElement = document.createElement( LEARN_MORE_TAG );
                    currentElement.setAttribute( URL_ATTRIBUTE, currentLink.getURL( ) );
                    currentElement.setAttribute( SEQ_ATTRIBUTE, currentLink.getPosition( ).toString( ) );
                    Element currentTitle = document.createElement( TITLE_TAG );
                    currentTitle.setTextContent( currentLink.getTitle( ) );
                    currentElement.appendChild( currentTitle );
                    document.getDocumentElement( ).appendChild( currentElement );
                    document.getDocumentElement( ).normalize( );
                }
            }

            List<ComplementaryDataLinkDTO> listTeleserviceLinks = _dilaComplementaryDataLinkService.findByDataId(
                    complementaryDataDTO.getId( ), ComplementaryLinkTypeEnum.TELESERVICE );
            if ( CollectionUtils.isNotEmpty( listTeleserviceLinks ) )
            {
                for ( ComplementaryDataLinkDTO currentLink : listTeleserviceLinks )
                {
                    Element currentElement = document.createElement( TELESERVICE_TAG );
                    currentElement.setAttribute( URL_ATTRIBUTE, currentLink.getURL( ) );
                    currentElement.setAttribute( SEQ_ATTRIBUTE, currentLink.getPosition( ).toString( ) );
                    currentElement.setAttribute( TYPE_ATTRIBUTE, PROPERTY_TELESERVICE );
                    currentElement.setTextContent( currentLink.getTitle( ) );
                    document.getDocumentElement( ).appendChild( currentElement );
                    document.getDocumentElement( ).normalize( );
                }
            }
        }
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComplementaryDataDTO findByCardAndAudience( Long ficheId, Long audienceId )
    {
        return _dilaComplementaryDataDAO.findByCardAndAudience( ficheId, audienceId );
    }
}
