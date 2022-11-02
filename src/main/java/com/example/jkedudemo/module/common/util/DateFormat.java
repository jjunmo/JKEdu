package com.example.jkedudemo.module.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
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
