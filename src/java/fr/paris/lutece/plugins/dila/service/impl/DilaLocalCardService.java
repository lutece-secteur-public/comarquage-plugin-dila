package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalCardDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalCardDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalCardService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Implementation of {@link IDilaLocalCardService}
 */
public class DilaLocalCardService implements IDilaLocalCardService, Serializable
{
    private static final String SEQ_ATTRIBUTE = "seq";

    private static final String ID_ATTRIBUTE = "ID";

    private static final String CARD_TAG = "Fiche";

    /** The serial ID */
    private static final long serialVersionUID = 3506441956169438006L;

    @Inject
    @Named( "dilaLocalCardDAO" )
    private ILocalCardDAO _dilaLocalCardDAO;

    @Override
    public Long create( LocalCardDTO fiche )
    {
        return _dilaLocalCardDAO.insert( fiche );
    }

    @Override
    public Long findCardIdByLocalId( String idLocal )
    {
        return _dilaLocalCardDAO.findCardIdByLocalId( idLocal );
    }

    @Override
    public void delete( Long localCardId )
    {
        _dilaLocalCardDAO.delete( localCardId );
    }

    @Override
    public LocalCardDTO findCardByLocalId( Long idLocal )
    {
        return _dilaLocalCardDAO.findCardByLocalId( idLocal );
    }

    @Override
    public void update( LocalCardDTO card )
    {
        _dilaLocalCardDAO.store( card );
    }

    @Override
    public boolean isCardWithParentId( String idLocal )
    {
        return _dilaLocalCardDAO.isCardWithParentId( idLocal );
    }

    @Override
    public Document insertCardLinks( String parentId, DocumentBuilder builder, Document document )
    {
        List<LocalCardDTO> cardsList = this.findLocalCardsByParentFolder( parentId );
        for ( LocalCardDTO currentCard : cardsList )
        {
            NodeList cards = document.getElementsByTagName( CARD_TAG );
            Element newCard = document.createElement( CARD_TAG );
            newCard.setAttribute( ID_ATTRIBUTE, currentCard.getLocalDTO( ).getId( ).toString( ) );
            newCard.setTextContent( currentCard.getLocalDTO( ).getTitle( ) );
            if ( StringUtils.isEmpty( currentCard.getSiblingCardId( ) ) )
            {
                if ( cards.getLength( ) > 0 )
                {
                    Element cardElement = (Element) cards.item( cards.getLength( ) - 1 );
                    cardElement.getParentNode( ).appendChild( newCard );
                }
                else
                {
                    document.appendChild( newCard );
                }
            }
            else
            {
                boolean hasToShift = false;
                for ( int i = 0; i < cards.getLength( ); i++ )
                {
                    Element cardElement = (Element) cards.item( i );
                    String seq = cardElement.getAttribute( SEQ_ATTRIBUTE );
                    if ( cardElement.getAttribute( ID_ATTRIBUTE ).equals( currentCard.getSiblingCardId( ) ) )
                    {
                        if ( currentCard.getPosition( ) == 1 )
                        {
                            if ( seq != null )
                            {
                                newCard.setAttribute( SEQ_ATTRIBUTE, "" + Integer.parseInt( seq ) );
                                cardElement.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                            }
                            cardElement.getParentNode( ).insertBefore( newCard, cardElement );
                        }
                        else
                        {
                            if ( seq != null )
                            {
                                newCard.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                            }
                            cardElement.getParentNode( ).insertBefore( newCard, cardElement.getNextSibling( ) );
                        }
                        i++;
                        hasToShift = true;
                    }
                    else if ( hasToShift && seq != null )
                    {
                        cardElement.setAttribute( SEQ_ATTRIBUTE, "" + ( Integer.parseInt( seq ) + 1 ) );
                    }
                }
            }
            document.getDocumentElement( ).normalize( );
        }

        return document;
    }

    @Override
    public List<LocalCardDTO> findLocalCardsByParentFolder( String idParent )
    {
        return _dilaLocalCardDAO.findLocalCardsByParentFolder( idParent );
    }
}
