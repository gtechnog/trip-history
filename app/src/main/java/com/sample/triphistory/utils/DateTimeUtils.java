package com.sample.triphistory.utils;

import androidx.annotation.Nullable;

import com.sample.triphistory.constant.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtils {

    @Nullable
    public static String getDisplayTime(String date) {
        if (date == null || date.isEmpty()) return date;
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat displayDateFormat = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT, Locale.ENGLISH);
        try {
            return displayDateFormat.format(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
