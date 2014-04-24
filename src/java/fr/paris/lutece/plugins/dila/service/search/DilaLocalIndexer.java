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
package fr.paris.lutece.plugins.dila.service.search;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.service.IDilaLocalService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.search.PageIndexer;
import fr.paris.lutece.portal.service.search.SearchIndexer;
import fr.paris.lutece.portal.service.search.SearchItem;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


/**
 * DILA local indexer
 */
public class DilaLocalIndexer implements SearchIndexer
{
    public static final String INDEX_TYPE_XML = "DILA Local";
    public static final String INDEXER_NAME = "DilaLocalIndexer";
    protected static final String PROPERTY_PAGE_BASE_URL = "dila.pageIndexer.baseUrl";
    protected static final String PROPERTY_SEARCH_PAGE_URL = "search.pageSearch.baseUrl";
    protected static final String PROPERTY_INDEXER_ENABLE = "dila.pageIndexer.enable";
    protected static final String PARAMETER_PAGE_ID = "xmlFile";
    protected static final String PARAMETER_PAGE_CATEGORIE = "categorie";
    private static final String INDEXER_DESCRIPTION = "DILA service for local";
    private static final String INDEXER_VERSION = "1.0.0";
    private IDilaLocalService _dilaLocalService = SpringContextService.getBean( "dilaLocalService" );

    @Override
    public void indexDocuments(  ) throws IOException, InterruptedException, SiteMessageException
    {
        List<LocalDTO> listLocal = _dilaLocalService.findAll(  );

        for ( LocalDTO local : listLocal )
        {
            Document doc = null;

            try
            {
                doc = getDocument( local );
            }
            catch ( Exception e )
            {
                String strMessage = "Page ID : " + local.getId(  );
                IndexationService.error( this, e, strMessage );
            }

            if ( doc != null )
            {
                IndexationService.write( doc );
            }
        }
    }

    @Override
    public List<Document> getDocuments( String strIdDocument )
        throws IOException, InterruptedException, SiteMessageException
    {
        return null;
    }

    @Override
    public String getName(  )
    {
        return INDEXER_NAME;
    }

    @Override
    public String getVersion(  )
    {
        return INDEXER_VERSION;
    }

    @Override
    public String getDescription(  )
    {
        return INDEXER_DESCRIPTION;
    }

    @Override
    public boolean isEnable(  )
    {
        String strEnable = AppPropertiesService.getProperty( PROPERTY_INDEXER_ENABLE, "true" );

        return ( strEnable.equalsIgnoreCase( "true" ) );
    }

    @Override
    public List<String> getListType(  )
    {
        List<String> listType = new ArrayList<String>(  );
        listType.add( PageIndexer.INDEX_TYPE_PAGE );

        return listType;
    }

    @Override
    public String getSpecificSearchAppUrl(  )
    {
        return AppPropertiesService.getProperty( PROPERTY_SEARCH_PAGE_URL );
    }

    /**
     * Builds a document which will be used by Lucene during the indexing of the
     * pages of the site with the following
     * fields : summary, uid, url, contents, title and description.
     * @return the built Document
     * @param local the xml to index
     * @throws IOException The IO Exception
     * @throws InterruptedException The InterruptedException
     * @throws SiteMessageException occurs when a site message need to be
     *             displayed
     */
    protected Document getDocument( LocalDTO local ) throws IOException, InterruptedException, SiteMessageException
    {
        String strPageBaseUrl = AppPropertiesService.getProperty( PROPERTY_PAGE_BASE_URL );

        // make a new, empty document
        Document doc = new Document(  );

        String strDate = DateTools.dateToString( local.getCreationDate(  ), DateTools.Resolution.DAY );
        doc.add( new Field( SearchItem.FIELD_DATE, strDate, Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        // Add the url as a field named "url".  Use an UnIndexed field, so
        // that the url is just stored with the document, but is not searchable.
        doc.add( new Field( SearchItem.FIELD_TYPE, local.getType(  ).getLabel(  ), Field.Store.YES,
                Field.Index.NOT_ANALYZED ) );

        // Add the url as a field named "url".  Use an UnIndexed field, so
        // that the url is just stored with the document, but is not searchable.
        UrlItem url = new UrlItem( strPageBaseUrl );
        url.addParameter( PARAMETER_PAGE_ID, "" + local.getId(  ) );
        url.addParameter( PARAMETER_PAGE_CATEGORIE, AudienceCategoryEnum.fromId( local.getIdAudience(  ) ).getLabel(  ) );

        doc.add( new Field( SearchItem.FIELD_URL, url.getUrl(  ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        StringBuilder content = new StringBuilder(  );
        content.append( local.getXml(  ) );

        doc.add( new Field( SearchItem.FIELD_CONTENTS, content.toString(  ), Field.Store.NO, Field.Index.ANALYZED ) );

        // Add the uid as a field, so that index can be incrementally maintained.
        // This field is not stored with document, it is indexed, but it is not
        // tokenized prior to indexing.
        String strIdPage = "" + local.getId(  );
        doc.add( new Field( SearchItem.FIELD_UID, strIdPage, Field.Store.NO, Field.Index.NOT_ANALYZED ) );

        // Add the tag-stripped contents as a Reader-valued Text field so it will
        // get tokenized and indexed.
        doc.add( new Field( SearchItem.FIELD_TITLE, local.getTitle(  ), Field.Store.YES, Field.Index.NOT_ANALYZED ) );

        // return the document
        return doc;
    }
}
