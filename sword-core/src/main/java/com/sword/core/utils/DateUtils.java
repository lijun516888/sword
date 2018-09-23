package com.sword.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat sf = new SimpleDateFormat();

    public static Date parse(String date) {
        try {
            sf.applyPattern("yyyy-MM-hh hh:mm:ss");
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
