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
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.presenter.entity.Save.SaveDownloadSeasonSeriePresenter;
import com.quiz.series.tvseriesquiz.presenter.entity.Save.SaveEntityPresenter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by Emilio on 14/10/2016.
 */

public class SyncronizeInteractorQuestion extends SyncronizeInteractor {
    private final int codeSerie, season;
    private final String language;

    public SyncronizeInteractorQuestion(final Executor executor, final MainThread mainThread, final int codeSerie, final String language, final int season) {
        super(executor, mainThread);
        this.context = MyApp.getContext();
        this.codeSerie = codeSerie;
        this.language = language;
        this.season = season;
    }

    public SyncronizeInteractorQuestion(final Executor executor, final MainThread mainThread, final Context context, final int codeSerie, final String language, final int season) {
        super(executor, mainThread);
        this.context = context;
        this.codeSerie = codeSerie;
        this.language = language;
        this.season = season;
    }

    @Override
    protected void startProcess() {
        startDownloadQuestions();
        notifySuccess();
    }

    private void startDownloadQuestions() {
        int MAX_BLOCK = 1;

        CountDownLatch lock = new CountDownLatch(MAX_BLOCK);

        startFirebase(new ADQuestionSchema(), lock);

        lock(lock);

    }

    @Override
    protected void startFirebase(final ADSchema schema, final CountDownLatch lock) {

        ADFirebase firebase = new ADFirebase(schema);
        firebase.downloader(codeSerie, language, season, new ADFirebaseInterface.Callback() {
            @Override
            public void onSuccess() {
                SaveEntityPresenter presenter = new SaveDownloadSeasonSeriePresenter(new ADSerieSchema(), codeSerie, season);
                presenter.initialize();

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
