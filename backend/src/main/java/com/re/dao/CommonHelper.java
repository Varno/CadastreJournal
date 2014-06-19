package com.re.dao;

import java.sql.Date;
import java.util.Calendar;

public class CommonHelper {

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
