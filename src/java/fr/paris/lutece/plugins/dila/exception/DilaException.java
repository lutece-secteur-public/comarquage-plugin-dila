package fr.paris.lutece.plugins.dila.exception;

/**
 * Manage daemon errors
 */
public class DilaException extends Exception
{
    /** the serial id */
    private static final long serialVersionUID = 4699854799828639612L;

    /**
     * Manage daemon error
     * @param strMessage the message
     */
    public DilaException( String strMessage )
    {
        super( strMessage );
    }

}
