package fr.paris.lutece.plugins.dila.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Specific date utilities
 * 
 */
public final class DateUtils
{
    /**
     * Default constructor
     */
    private DateUtils( )
    {

    }

    /**
     * Get date duration formatted "HH:mm:ss:SSS"
     * @param start date begin
     * @param end date end
     * @return the duration
     */
    public static String getDuration( long start, long end )
    {
        long duration = end - start;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm:ss:SSS" );
        Calendar calendar = Calendar.getInstance( );
        calendar.setTimeInMillis( duration );
        return simpleDateFormat.format( calendar.getTime( ) );
    }

    /**
     * Get date duration formatted "yyyy-MM-dd"
     * @param date date to convert
     * @return date formatted
     */
    public static String convert( Date date )
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        return simpleDateFormat.format( date );
    }
}
