package com.quiz.series.tvseriesquiz.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by equipo on 24/07/2015.
 */
public class DisplayUtils {

    public static int getDisplayWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getDisplayHeight(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static String getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        String resolution = String.valueOf(metrics.density);
        return resolution;
    }
}
