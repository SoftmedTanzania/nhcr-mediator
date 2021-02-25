package tz.go.moh.him.nhcr.mediator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Utils {
    /**
     * Handles checking for the correct date string format from a variety of formats
     *
     * @param dateString of the date
     * @return the matching date string format
     */
    public static Date checkDateFormatStrings(String dateString) {
        List<String> formatStrings = Arrays.asList("yyyy-MM-dd HH:mm:ss:ms", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyyMMdd");

        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(dateString);
            } catch (ParseException e) {
                //Invalid Date String
            }
        }

        return null;
    }
}
