package ru.yandex.yandex_backend_java.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// https://gist.github.com/kristopherjohnson/6124652
public class TimestampUtils {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    static {
//        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
//        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Almaty"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private TimestampUtils() {
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date stringToDate(String s) throws ParseException {
        return dateFormat.parse(s);
    }

    public static boolean matchesISO8601(String date) {
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}