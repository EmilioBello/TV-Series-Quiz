package com.quiz.series.tvseriesquiz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebaseUser;


public class MyApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        //get Context
        context = getApplicationContext();

        //init Catalog on RAM
        checkLanguage();
    }

    private void checkLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
    }

    public static Context getContext() {
        return context;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}