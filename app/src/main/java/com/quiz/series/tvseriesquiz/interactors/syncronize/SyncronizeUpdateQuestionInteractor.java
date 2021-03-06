package com.quiz.series.tvseriesquiz.interactors.syncronize;

import android.content.Context;
import android.util.Log;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebase;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebaseInterface;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by Emilio on 23/08/2017.
 */

public class SyncronizeUpdateQuestionInteractor extends SyncronizeInteractor {
    private final int codeSerie;
    private final long update;
    private final String language;

    public SyncronizeUpdateQuestionInteractor(final Executor executor, final MainThread mainThread, final int codeSerie, final String language, final long update) {
        super(executor, mainThread);
        this.context = MyApp.getContext();
        this.codeSerie = codeSerie;
        this.language = language;
        this.update = update;
    }

    public SyncronizeUpdateQuestionInteractor(final Executor executor, final MainThread mainThread, final Context context, final int codeSerie, final String language, final long update) {
        super(executor, mainThread);
        this.context = context;
        this.codeSerie = codeSerie;
        this.language = language;
        this.update = update;
    }

    @Override
    protected void startProcess() {
        startDownloadUpdateQuestions();
        notifySuccess();
    }

    private void startDownloadUpdateQuestions() {
        int MAX_BLOCK = 1;

        CountDownLatch lock = new CountDownLatch(MAX_BLOCK);

        startFirebase(new ADQuestionSchema(), lock);

        lock(lock);

    }

    @Override
    protected void startFirebase(final ADSchema schema, final CountDownLatch lock) {

        ADFirebase firebase = new ADFirebase(schema);
        firebase.downloaderFirstTime(codeSerie, language, update, new ADFirebaseInterface.Callback() {
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
}
