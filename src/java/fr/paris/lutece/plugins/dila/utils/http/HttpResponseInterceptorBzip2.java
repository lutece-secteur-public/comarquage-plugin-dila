package fr.paris.lutece.plugins.dila.utils.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;


/**
 * {@link HttpResponseInterceptor} to accept bzip2 content
 * 
 */
public class HttpResponseInterceptorBzip2 implements HttpResponseInterceptor
{

    @Override
    public void process( HttpResponse response, HttpContext context ) throws HttpException, IOException
    {
        HttpEntity entity = response.getEntity( );
        if ( entity != null )
        {
            Header ceheader = entity.getContentEncoding( );
            if ( ceheader != null )
            {
                HeaderElement[] codecs = ceheader.getElements( );
                for ( int i = 0; i < codecs.length; i++ )
                {
                    if ( codecs[i].getName( ).equalsIgnoreCase( "x-bzip2" ) )
                    {
                        response.setEntity( new Bzip2Entity( response.getEntity( ) ) );
                        return;
                    }
                }
            }
        }

    }

}
