package fr.paris.lutece.plugins.dila.business.enums;

/**
 * Enum for action type
 */
public enum ActionTypeEnum
{
    CREATE( "CREATE" ), MODIFY( "MODIFY" );

    private String _strValue;

    /**
     * Constructor
     * @param strValue the label
     */
    private ActionTypeEnum( String strValue )
    {
        this._strValue = strValue;
    }

    /**
     * @return the _strValue
     */
    public String getValue( )
    {
        return _strValue;
    }
}
