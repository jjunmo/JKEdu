package com.example.jkedudemo.module.common.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String toDateFormat(Date date) {
        if(date!=null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(date);
        }else{
            return null;
        }
    }
}
