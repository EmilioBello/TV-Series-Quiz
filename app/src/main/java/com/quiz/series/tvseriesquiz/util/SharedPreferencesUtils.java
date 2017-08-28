package com.quiz.series.tvseriesquiz.util;

/**
 * Created by Emilio on 30/06/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

public class SharedPreferencesUtils {

    public static void saveActualTime(final Context context, final String key){
        Calendar calendar = Calendar.getInstance();
        saveDate(context, key, calendar.getTimeInMillis());
    }

    public static void saveDate(final Context context, final String key, long date){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(key, date);
        editor.apply();
    }

    public static long getDate(String key, Context context) {
        android.content.SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, 0);
    }
}
