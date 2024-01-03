package com.dpworld;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDateString(Date date, String format) {
        String rs = "";
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            rs = dateFormat.format(date);
        }
        return rs;
    }
}
