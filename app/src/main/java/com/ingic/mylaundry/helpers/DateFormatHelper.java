package com.ingic.mylaundry.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatHelper {

    public static String formatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        format = new SimpleDateFormat("d MMM, yyyy");
        return format.format(newDate);
    }

    public static String ConfirmPaymentformatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        format = new SimpleDateFormat("MM-yy");
        return format.format(newDate);
    }

    public static String serverDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(newDate);
    }
/*
    public static String formatDate(String date)
	{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
		Date newDate = null;
		try {
			newDate = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		format = new SimpleDateFormat("dd MMM, YYYY");
		return format.format(newDate);
	}
*/


    public static String changeToServerFormatTime(String date) {
        if (!Utils.isEmptyOrNull(date)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy",Locale.US);
                //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date newDate = null;
                newDate = format.parse(date);
                format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                return format.format(newDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return date;
    }

    public static String changeServerTime(String time) {
        String inputPattern = "HH:mm:ss";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String changeServerToShowFormatTime(String date) {
        if (!Utils.isEmptyOrNull(date)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
                Date newDate = null;
                newDate = format.parse(date);
                format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                return format.format(newDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return date;

    }

    public static String changeServerToEventDetailShowFormatTime(String date) {
        if (!Utils.isEmptyOrNull(date)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = null;
                newDate = format.parse(date);
                format = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.US);
                return format.format(newDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return date;

    }

    public static String changeEventFormatTime(String date) {
        if (!Utils.isEmptyOrNull(date)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
                Date newDate = null;
                newDate = format.parse(date);
                format = new SimpleDateFormat(" dd MMM, yyyy", Locale.US);
                return format.format(newDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return date;

    }

    public static String formatTime(String date) {
        try {
            String[] splitTime= date.split("-");

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss",Locale.US);
            Date newDate,newDate_ = null;
            newDate = format.parse(splitTime[0]);
            newDate_ = format.parse(splitTime[1]);

            format = new SimpleDateFormat("hh:mm a", Locale.US);
            return format.format(newDate).replace("a.m.","A.M.").replace("p.m.","P.M.")+"-"+format.format(newDate_).replace("a.m.","A.M.").replace("p.m.","P.M.");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;

    }

    public static String changeformatTime(String date) {
        try {
            String[] splitTime= date.split("-");

            SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a",Locale.US);
            Date newDate,newDate_ = null;
            newDate = format1.parse(splitTime[0]);
            newDate_ = format1.parse(splitTime[1]);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
            return format.format(newDate)+" - "+format.format(newDate_);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;

    }
}
