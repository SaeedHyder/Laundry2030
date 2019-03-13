package com.ingic.mylaundry.firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khanubaid on 1/4/2018.
 */

public class ServiceDate {
    private Date date;

    public static final String DATE_FORMAT = "MMM dd, yyyy | EEE";
    public static final String DATE_FORMAT_2 = "dd-mm-yyyy";
    public static final String DAY_FORMAT = "EEEEEEE";
    public static final String TIME_FORMAT = "h:mm a";

    public String getDayFormatted() {

        SimpleDateFormat sdf = new SimpleDateFormat( DAY_FORMAT );
        return sdf.format( getDate() );

    }

    public String getDateFormatted() {

        SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT_2 );
        return sdf.format( getDate() );

    }

    public String getTimeFormatted() {

        SimpleDateFormat sdf = new SimpleDateFormat( TIME_FORMAT );
        return sdf.format( getDate() );

    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }
}
