package com.sample.triphistory.utils;

import androidx.annotation.Nullable;

import com.sample.triphistory.constant.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utility class for handling the date time formats and conversions
 */
public class DateTimeUtils {

    @Nullable
    public static String getDisplayTime(@Nullable String date) {
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

    public static long getTimeMillies(String dateString) {
        if (dateString == null || dateString.isEmpty()) return 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
        long time = 0;
        try {
            Date date = dateFormat.parse(dateString);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            time = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
