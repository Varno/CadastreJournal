package com.re.dao;

import java.sql.Timestamp;
import java.util.Calendar;

public class CommonHelper {
    public static String BASE_PATH;
    public static Calendar dateToCalendar(Timestamp dateTime){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        return cal;
    }
}
