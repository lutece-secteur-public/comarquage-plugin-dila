package fr.paris.lutece.plugins.dila.utils.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;


/**
 * {@link HttpEntityWrapper} to rewrite content encoding and content length to
 * accept bzip2
 * 
 * 
 */
public class Bzip2Entity extends HttpEntityWrapper
{
    /**
     * Constructor
     * @param wrappedEntity the entity
     */
    public Bzip2Entity( HttpEntity wrappedEntity )
    {
        super( wrappedEntity );
    }

    @Override
    public Header getContentEncoding( )
    {
        return null;
    }

    @Override
    public long getContentLength( )
    {
        return -1;
    }

}
