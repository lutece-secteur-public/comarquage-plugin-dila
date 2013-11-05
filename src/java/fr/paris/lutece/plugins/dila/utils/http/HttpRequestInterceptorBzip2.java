package fr.paris.lutece.plugins.dila.utils.http;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;


/**
 * {@link HttpRequestInterceptorBzip2} to accept bzip2 content
 * 
 */
public class HttpRequestInterceptorBzip2 implements HttpRequestInterceptor
{

    @Override
    public void process( HttpRequest request, HttpContext context ) throws HttpException, IOException
    {
        if ( !request.containsHeader( "Accept-Encoding" ) )
        {
            request.addHeader( "Accept-Encoding", "x-bzip2" );
        }
    }

}
