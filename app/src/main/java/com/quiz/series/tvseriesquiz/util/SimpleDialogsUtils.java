package com.quiz.series.tvseriesquiz.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by jose on 29/6/15.
 */
public class SimpleDialogsUtils {

    public static AlertDialog singleButtonDialog( Context context, String title, String message, String buttonText, DialogInterface.OnClickListener buttonListener, int theme) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, theme);
        builder.setNeutralButton(buttonText, buttonListener);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder.create();
    }

    public static AlertDialog singleButtonDialog( Context context, String title, String message, DialogInterface.OnClickListener buttonListener, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNeutralButton(buttonText, buttonListener);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder.create();
    }
}
