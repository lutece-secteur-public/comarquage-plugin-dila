package fr.paris.lutece.plugins.dila.business.fichelocale.dao.impl;

import fr.paris.lutece.plugins.dila.business.enums.ResourceTypeEnum;
import fr.paris.lutece.plugins.dila.business.fichelocale.dao.IXmlDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.XmlDTO;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link IXmlDAO}
 */
public class XmlDAO implements IXmlDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -907799297326123041L;

    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_xml";
    private static final String SQL_QUERY_FIND_TITLE_BY_ID = "SELECT title FROM dila_xml WHERE id_xml = ? ";
    private static final String SQL_QUERY_FIND_FOLDER_BY_ID = "SELECT title, breadcrumb FROM dila_xml WHERE id_xml = ? ";
    private static final String SQL_QUERY_FIND_TITLE_BY_ID_AND_TYPES_AND_AUDIENCE = "SELECT title FROM dila_xml "
            + "WHERE id_xml = ? AND fk_audience_id = ? ";
    private static final String SQL_QUERY_FIND_FODLER_BY_ID_AND_TYPES_AND_AUDIENCE = "SELECT id, title, breadcrumb FROM dila_xml "
            + "WHERE id_xml = ? AND fk_audience_id = ? ";
    private static final String SQL_QUERY_AND_RESOURCE_TYPE = "AND type_resource IN ";
    private static final String SQL_QUERY_FIND_RESOURCE_TYPE_BY_IDXML_AND_AUDIENCE = "SELECT type_resource FROM dila_xml"
            + " WHERE id_xml = ? AND fk_audience_id = ?";
    private static final String SQL_QUERY_FIND_ALL = "SELECT id, id_xml, title, breadcrumb, fk_audience_id, type_resource, date_creation, date_modification FROM dila_xml ";
    private static final String SQL_QUERY_FIND_HOW_TO_BY_AUDIENCE = "SELECT id_xml, title FROM dila_xml "
            + "WHERE fk_audience_id = ? AND type_resource = ? ORDER BY date_modification DESC";
    private static final String SQL_QUERY_FIND_ID_BY_XML_AND_AUDIENCE = "SELECT id FROM dila_xml "
            + "WHERE id_xml = ? AND fk_audience_id = ?";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_xml ( id, id_xml , title , type_resource, breadcrumb, fk_audience_id, date_creation, date_modification ) "
            + " VALUES ( ?, ? ,?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_xml SET title = ?, type_resource = ?, breadcrumb = ?, date_modification = ? WHERE id_xml = ? AND fk_audience_id = ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM dila_xml WHERE date_modification < ?";

    /**
     * Generates a new primary key
     * @return The new primary key
     */
    private Long newPrimaryKey( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.executeQuery( );

        Long nKey = 1L;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getLong( 1 ) + 1L;
        }

        daoUtil.free( );

        return nKey;
    }

    @Override
    public String findTitleById( String id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_TITLE_BY_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, id );

        daoUtil.executeQuery( );

        String title = null;

        if ( daoUtil.next( ) )
        {
            title = daoUtil.getString( 1 );
        }

        daoUtil.free( );

        return title;
    }

    @Override
    public XmlDTO findFolderById( String id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_FOLDER_BY_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, id );

        daoUtil.executeQuery( );

        XmlDTO xml = null;

        if ( daoUtil.next( ) )
        {
            xml = new XmlDTO( );

            xml.setIdXml( id );
            xml.setTitle( daoUtil.getString( 1 ) );
            xml.setBreadcrumb( daoUtil.getString( 2 ) );
        }

        daoUtil.free( );

        return xml;
    }

    @Override
    public String findTitleByIdAndTypesAndAudience( String idDossierFrere, List<String> availableTypes, Long idAudience )
    {
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_FIND_TITLE_BY_ID_AND_TYPES_AND_AUDIENCE );

        sbSQL.append( " " );
        sbSQL.append( SQL_QUERY_AND_RESOURCE_TYPE );
        sbSQL.append( "(" );

        int count = 0;
        for ( String type : availableTypes )
        {
            count++;
            if ( count < availableTypes.size( ) )
            {
                sbSQL.append( "'" + type + "'," );
            }
            else
            {
                sbSQL.append( "'" + type + "'" );
            }
        }

        sbSQL.append( ")" );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString( ), PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, idDossierFrere );
        daoUtil.setLong( 2, idAudience );

        daoUtil.executeQuery( );

        String title = null;

        if ( daoUtil.next( ) )
        {
            title = daoUtil.getString( 1 );
        }

        daoUtil.free( );

        return title;
    }

    @Override
    public XmlDTO findByIdAndTypesAndAudience( String strId, List<String> availableTypes, Long lIdAudience )
    {
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_FIND_FODLER_BY_ID_AND_TYPES_AND_AUDIENCE );

        sbSQL.append( " " );
        sbSQL.append( SQL_QUERY_AND_RESOURCE_TYPE );
        sbSQL.append( "(" );

        int count = 0;
        for ( String type : availableTypes )
        {
            count++;
            if ( count < availableTypes.size( ) )
            {
                sbSQL.append( "'" + type + "'," );
            }
            else
            {
                sbSQL.append( "'" + type + "'" );
            }
        }

        sbSQL.append( ")" );

        DAOUtil daoUtil = new DAOUtil( sbSQL.toString( ), PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, strId );
        daoUtil.setLong( 2, lIdAudience );

        daoUtil.executeQuery( );

        XmlDTO xml = null;

        if ( daoUtil.next( ) )
        {
            xml = new XmlDTO( );

            xml.setId( daoUtil.getLong( 1 ) );
            xml.setIdXml( strId );
            xml.setTitle( daoUtil.getString( 2 ) );
            if ( daoUtil.getString( 3 ) != null )
            {
                xml.setBreadcrumb( daoUtil.getString( 3 ) );
            }
            else
            {
                xml.setBreadcrumb( "" );
            }
        }

        daoUtil.free( );

        return xml;
    }

    @Override
    public String findResourceTypeByIdXMLAndAudience( String strIdXml, Long lIdAudience )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_RESOURCE_TYPE_BY_IDXML_AND_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, strIdXml );
        daoUtil.setLong( 2, lIdAudience );

        daoUtil.executeQuery( );

        String typeResource = null;

        if ( daoUtil.next( ) )
        {
            typeResource = daoUtil.getString( 1 );
        }

        daoUtil.free( );

        return typeResource;
    }

    @Override
    public List<XmlDTO> findAll( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ALL, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.executeQuery( );

        List<XmlDTO> result = new ArrayList<XmlDTO>( );

        while ( daoUtil.next( ) )
        {
            XmlDTO xml = new XmlDTO( );
            xml.setId( daoUtil.getLong( 1 ) );
            xml.setIdXml( daoUtil.getString( 2 ) );
            xml.setTitle( daoUtil.getString( 3 ) );
            xml.setBreadcrumb( daoUtil.getString( 4 ) );
            xml.setIdAudience( daoUtil.getLong( 5 ) );
            xml.setResourceType( daoUtil.getString( 6 ) );
            xml.setCreationDate( daoUtil.getDate( 7 ) );
            xml.setModificationDate( daoUtil.getDate( 8 ) );

            result.add( xml );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public List<XmlDTO> findHowToByAudience( Long audienceId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_HOW_TO_BY_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, audienceId );
        daoUtil.setString( 2, ResourceTypeEnum.HOW_TO.getLabel( ) );

        List<XmlDTO> result = new ArrayList<XmlDTO>( );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            XmlDTO dto = new XmlDTO( );

            dto.setIdXml( daoUtil.getString( 1 ) );
            dto.setTitle( daoUtil.getString( 2 ) );

            result.add( dto );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public Long findIdByXmlAndAudience( String xmlName, Long audienceId )
    {
        Long id = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ID_BY_XML_AND_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setString( 1, xmlName );
        daoUtil.setLong( 2, audienceId );
        daoUtil.executeQuery( );
        if ( daoUtil.next( ) )
        {
            id = daoUtil.getLong( 1 );
        }
        daoUtil.free( );
        return id;
    }

    @Override
    public XmlDTO findHomeHowTo( Long audienceId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_HOW_TO_BY_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, audienceId );
        daoUtil.setString( 2, ResourceTypeEnum.HOW_TO_HOME.getLabel( ) );

        daoUtil.executeQuery( );
        XmlDTO homeHowTo = null;
        if ( daoUtil.next( ) )
        {
            homeHowTo = new XmlDTO( );
            homeHowTo.setIdXml( daoUtil.getString( 1 ) );
            homeHowTo.setTitle( daoUtil.getString( 2 ) );
        }
        daoUtil.free( );
        return homeHowTo;
    }

    @Override
    public void create( XmlDTO dilaXml )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        dilaXml.setId( newPrimaryKey( ) );

        java.util.Date current = new java.util.Date( );

        daoUtil.setLong( 1, dilaXml.getId( ) );
        daoUtil.setString( 2, dilaXml.getIdXml( ) );
        daoUtil.setString( 3, dilaXml.getTitle( ) );
        daoUtil.setString( 4, dilaXml.getResourceType( ) );
        daoUtil.setString( 5, dilaXml.getBreadcrumb( ) );
        daoUtil.setLong( 6, dilaXml.getIdAudience( ) );
        daoUtil.setDate( 7, new Date( current.getTime( ) ) );
        daoUtil.setDate( 8, new Date( current.getTime( ) ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void store( XmlDTO dilaXml )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        java.util.Date current = new java.util.Date( );

        daoUtil.setString( 1, dilaXml.getTitle( ) );
        daoUtil.setString( 2, dilaXml.getResourceType( ) );
        daoUtil.setString( 3, dilaXml.getBreadcrumb( ) );
        daoUtil.setDate( 4, new Date( current.getTime( ) ) );
        daoUtil.setString( 5, dilaXml.getIdXml( ) );
        daoUtil.setLong( 6, dilaXml.getIdAudience( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void delete( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        java.util.Date current = new java.util.Date( );
        daoUtil.setDate( 1, new Date( current.getTime( ) ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
