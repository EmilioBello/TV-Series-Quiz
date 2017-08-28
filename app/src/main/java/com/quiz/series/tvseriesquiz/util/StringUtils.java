package com.quiz.series.tvseriesquiz.util;

import java.util.Calendar;

/**
 * Some utility methods related with the String class.
 *
 * */
public class StringUtils {

    private static final String EMPTY_STRING = "";

    private StringUtils() {
        //Empty
    }

    public static boolean isNullOrEmpty(final String string) {
        return string == null || EMPTY_STRING.equals(string);
    }

    public static String getDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "D";

            case Calendar.MONDAY:
                return "L";

            case Calendar.TUESDAY:
                return "M";

            case Calendar.WEDNESDAY:
                return "X";

            case Calendar.THURSDAY:
                return "J";

            case Calendar.FRIDAY:
                return "V";

            case Calendar.SATURDAY:
                return "S";
        }

        return "";
    }
}
