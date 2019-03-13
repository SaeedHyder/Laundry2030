package com.ingic.mylaundry.firebase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khanubaid on 1/4/2018.
 */

public class ServiceDateTime {

    Date date;

    public ServiceDateTime() {
    }

    public ServiceDateTime( String strDate ) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy" );

        try {
            date = simpleDateFormat.parse( strDate );
        } catch ( ParseException e ) {

            e.printStackTrace();
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }
}
