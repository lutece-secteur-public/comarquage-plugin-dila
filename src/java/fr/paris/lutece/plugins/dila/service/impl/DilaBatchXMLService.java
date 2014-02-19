/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.enums.AudienceCategoryEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IXmlDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.IDilaBatchXMLService;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;


/**
 * Implementation of {@link IDilaBatchXMLService}
 */
public class DilaBatchXMLService implements IDilaBatchXMLService
{

    /**
     * List of XML file that will not be indexed
     */
    public static final List<String> listXMLNotSupported = Arrays.asList( "arborescence.xml", "redirections.xml",
            "journal.xml" );

    /**
     * Type of resource
     */
    private static final String RESSOURCE_TYPE = "Ressource";

    /**
     * SAX builder
     */
    private static final SAXBuilder _saxBuilder = new SAXBuilder( );

    /**
     * URI for namespace "dc"
     */
    private static final String NAMESPACE_URI_DC = "http://purl.org/dc/elements/1.1/";

    /**
     * Namespace "dc"
     */
    private static final Namespace NAMESPACE_DC = Namespace.getNamespace( "dc", NAMESPACE_URI_DC );

    /**
     * XML attribute : ID
     */
    private static final String ATTRIBUTE_ID = "ID";

    /**
     * XML child : title
     */
    private static final String CHILD_TITLE = "title";

    /**
     * XML child : type
     */
    private static final String CHILD_TYPE = "type";

    /**
     * XML child : FilDAriane
     */
    private static final String CHILD_BREADCRUMB = "FilDAriane";

    @Inject
    @Named( "dilaXmlDAO" )
    private IXmlDAO _dilaXmlDAO;

    @Override
    public void processXMLFile( File file, AudienceCategoryEnum typeXML ) throws JDOMException, IOException
    {
        AppLogService.debug( "Processing file : " + file.getName( ) );

        if ( DilaBatchXMLService.listXMLNotSupported.contains( file.getName( ).toLowerCase( ) ) )
        {
            AppLogService.debug( "File not indexed" );
        }
        else
        {
            // DILA XML
            XmlDTO dilaXml = buildDocument( file, typeXML );

            // Create or update XML
            List<XmlDTO> listDilaXmls = _dilaXmlDAO.findAll( );
            int nIndex = listDilaXmls.indexOf( dilaXml );

            if ( nIndex != -1 )
            {
                XmlDTO dilaXmlExist = listDilaXmls.get( nIndex );

                AppLogService.debug( "Update" );
                dilaXml.setId( dilaXmlExist.getId( ) );
                _dilaXmlDAO.store( dilaXml );
            }
            else
            {
                AppLogService.debug( "Create" );
                _dilaXmlDAO.create( dilaXml );
            }
        }
    }

    @Override
    public XmlDTO buildDocument( File file, AudienceCategoryEnum typeXML ) throws JDOMException, IOException
    {
        XmlDTO dilaXml = new XmlDTO( );
        dilaXml.setIdAudience( typeXML.getId( ) );

        // Convert file to document object
        Document document = _saxBuilder.build( file );

        // Get root
        Element root = document.getRootElement( );

        // Get attribute ID
        Attribute rootAttrId = root.getAttribute( ATTRIBUTE_ID );

        if ( rootAttrId != null )
        {
            String strId = rootAttrId.getValue( );
            dilaXml.setIdXml( strId );
        }

        // Get title
        Element title = root.getChild( CHILD_TITLE, NAMESPACE_DC );

        if ( title != null )
        {
            String strTitle = title.getTextTrim( );
            dilaXml.setTitle( strTitle );
        }

        // Get type
        Element type = root.getChild( CHILD_TYPE, NAMESPACE_DC );

        if ( type != null )
        {
            if ( file.getName( ).startsWith( "R" ) )
            {
                dilaXml.setResourceType( RESSOURCE_TYPE );
            }
            else
            {
                String strType = type.getTextTrim( );
                dilaXml.setResourceType( strType );
            }
        }

        // Get breadcrumb
        Element rootBreadcrumb = root.getChild( CHILD_BREADCRUMB );

        if ( rootBreadcrumb != null )
        {
            List<Element> listBreadcrumb = rootBreadcrumb.getChildren( );

            if ( CollectionUtils.isNotEmpty( listBreadcrumb ) )
            {
                List<String> listBreadcrumbId = new ArrayList<String>( listBreadcrumb.size( ) );

                for ( Element elementBreadcrumb : listBreadcrumb )
                {
                    listBreadcrumbId.add( elementBreadcrumb.getAttributeValue( ATTRIBUTE_ID ) );
                }

                String strBreadcrumb = StringUtils.join( listBreadcrumbId, ";" );
                dilaXml.setBreadCrumb( strBreadcrumb );
            }
        }

        return dilaXml;
    }

    @Override
    public void delete( )
    {
        _dilaXmlDAO.delete( );
    }
}
