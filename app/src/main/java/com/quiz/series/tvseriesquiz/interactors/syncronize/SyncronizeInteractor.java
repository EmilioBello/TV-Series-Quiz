package com.quiz.series.tvseriesquiz.interactors.syncronize;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.catalog.LanguageCatalog;
import com.quiz.series.tvseriesquiz.catalog.LoadedCatalog;
import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebase;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebaseInterface;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADVersionSchema;

import java.util.Calendar;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static com.quiz.series.tvseriesquiz.util.SharedPreferencesUtils.saveActualTime;

/**
 * Created by Emilio on 30/06/2016.
 */

public abstract class SyncronizeInteractor implements SyncronizeInterface,LoadedCatalog,Runnable {

    protected Executor executor;
    protected MainThread mainThread;
    protected Callback callback;

    //error
    protected boolean error;
    protected String messageError;

    //data
    public HashSet<String> versions;

    //Context
    protected Context context;

    //Language
    final protected CountDownLatch lockLanguage = new CountDownLatch(1);


    public SyncronizeInteractor(Executor executor, MainThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;

        this.error = false;
        this.messageError = "";
    }

    @Override
    public void execute(Callback callback) {
        this.callback = callback;
        executor.execute(this);
    }

    @Override
    public void run() {
        if (isNetworkAvailable()) {
            startProcess();
        } else {
            notifyError();
        }
    }

    protected abstract void startProcess();

    protected void downloadVersions() {
        final CountDownLatch lock = new CountDownLatch(1);

        //check version
        ADFirebase firebase = new ADFirebase(new ADVersionSchema());

        firebase.downloaderVersion(new ADFirebaseInterface.CallbackVersion() {
            @Override
            public void onSuccess(final HashSet<String> entities) {
                versions = entities;
                lock.countDown();
            }

            @Override
            public void onError(String message) {
                error = true;
                messageError = message;
                notifyError();
            }
        });

        lock(lock);
        Log.i(ADConstants.APPNAME, "Finished versions");
    }


    protected void checkSchema(final ADSchema schema, final CountDownLatch lock) {
        if (versions.contains(schema.getNameDBOnline())) {
            startFirebase(schema, lock);
        } else {
            lock.countDown();
        }
    }


    protected void startFirebase(final ADSchema schema, final CountDownLatch lock) {
        //check version

        ADFirebase firebase = new ADFirebase(schema);
        firebase.downloader(new ADFirebaseInterface.Callback() {
            @Override
            public void onSuccess() {
                Log.i(ADConstants.APPNAME, "success " + schema.getNameDBOnline() + "(" + String.valueOf(lock.getCount()) + ")");
                lock.countDown();
            }

            @Override
            public void onError(String message) {
                Log.i(ADConstants.APPNAME, message);
                lock.countDown();
            }
        });
    }

    protected void finishDownload(String update) {
        //initCatalog(false);

        saveActualTime(context, update);
        lock(lockLanguage);
        notifySuccess();
    }

    //Load Language catalog to optimize syncronize algorithm
    protected void loadLanguage(){
        LanguageCatalog languageCatalog = null;
        languageCatalog = new LanguageCatalog(this);
        languageCatalog.loadCatalog();
    }

    @Override
    public void loaded(){
        lockLanguage.countDown();
    }

    protected void lock(CountDownLatch lock) {
        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (error) {
            notifyError();
        }
    }

    protected void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.notifyError(messageError);
            }
        });
    }

    protected void notifySuccess() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.notifySuccess(true);
            }
        });
    }

    protected void notifyDownload() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.notifyDownload();
            }
        });
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            error = true;
            messageError = "Impossible connecting to data";
            return false;
        }
    }
}
