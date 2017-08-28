package com.quiz.series.tvseriesquiz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils {

    public static Long convertDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date convertLongToDate(Long dateAsLong) {
        if (dateAsLong == null) {
            return null;
        }
        return new Date(dateAsLong);
    }

    public static String getNaturalDay(Date date) {
        if (DateUtils.isBeforeToday(date)) {
            return "HOY";
        } else if (DateUtils.isToday(date)) {
            return "HOY";
        } else if (DateUtils.isTomorrow(date)) {
            return "MAÑANA";
        } else {
            return DateUtils.getDayOfWeek(date) + " " + DateUtils.getDay(date) + ", " + DateUtils.getMonthOfYear(date);
        }
    }

    public static boolean isBeforeToday(Date date) {
        boolean beforeToday;

        Calendar calendarToday = Calendar.getInstance();
        int dayToCompare = calendarToday.get(Calendar.DAY_OF_YEAR);
        int yearToCompare = calendarToday.get(Calendar.YEAR);
        calendarToday.setTimeZone(TimeZone.getTimeZone("UTC"));

        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        if(dayToCompare > day && yearToCompare == year){
            beforeToday = true;
        }
        else if(yearToCompare > year){
            beforeToday = true;
        }
        else{
            beforeToday = false;
        }

        return beforeToday;
    }

    public static boolean isToday(Date date) {
        Calendar calendarToday = Calendar.getInstance();
        int dayToday = calendarToday.get(Calendar.DAY_OF_YEAR);
        int yearCurrent = calendarToday.get(Calendar.YEAR);
        calendarToday.setTimeZone(TimeZone.getTimeZone("UTC"));

        //
        Calendar calendar = Calendar.getInstance();
        calendarToday.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        return (dayToday == day && yearCurrent == year);
    }

    public static boolean isTomorrow(Date date) {
        //
        Calendar calendarTomorrow = Calendar.getInstance();
        calendarTomorrow.setTimeZone(TimeZone.getTimeZone("UTC"));

        calendarTomorrow.add(Calendar.DATE, 1);
        int dayTomorrow = calendarTomorrow.get(Calendar.DAY_OF_YEAR);
        int yearCurrent = calendarTomorrow.get(Calendar.YEAR);

        //
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        return (dayTomorrow == day && yearCurrent == year);
    }

    public static String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Dom";

            case Calendar.MONDAY:
                return "Lun";

            case Calendar.TUESDAY:
                return "Mar";

            case Calendar.WEDNESDAY:
                return "Mie";

            case Calendar.THURSDAY:
                return "Jue";

            case Calendar.FRIDAY:
                return "Vie";

            case Calendar.SATURDAY:
                return "Sab";
        }

        return "";
    }

    public static String getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        calendar.setTime(date);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        return day;
    }

    public static String getMonthOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);

        switch (month) {
            case Calendar.JANUARY:
                return "Ene";

            case Calendar.FEBRUARY:
                return "Feb";

            case Calendar.MARCH:
                return "Mar";

            case Calendar.APRIL:
                return "Abr";

            case Calendar.MAY:
                return "May";

            case Calendar.JUNE:
                return "Jun";

            case Calendar.JULY:
                return "Jul";

            case Calendar.AUGUST:
                return "Ago";

            case Calendar.SEPTEMBER:
                return "Sep";

            case Calendar.OCTOBER:
                return "Oct";

            case Calendar.NOVEMBER:
                return "Nov";

            case Calendar.DECEMBER:
                return "Dic";
        }

        return "";
    }

    public static String getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        calendar.setTime(date);


        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return  dateFormat.format(date);
    }

    public static long diffHour(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if (hours < 0)  {
            hours = hours * (-1);
        }

        return hours;
    }

    public static long diffMinutes(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;

        if (minutes < 0)  {
            minutes = minutes * (-1);
        }

        return minutes;
    }
}
