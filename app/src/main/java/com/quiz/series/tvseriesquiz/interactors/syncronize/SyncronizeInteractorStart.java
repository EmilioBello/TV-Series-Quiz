package com.quiz.series.tvseriesquiz.interactors.syncronize;

/**
 * Created by Emilio on 30/06/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebase;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.ADFirebaseInterface;
import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADLanguageSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.util.LanguageUtils;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;


public class SyncronizeInteractorStart extends SyncronizeInteractor {

    public SyncronizeInteractorStart(Executor executor, MainThread mainThread) {
        super(executor, mainThread);
        this.context = MyApp.getContext();
    }

    public SyncronizeInteractorStart(Executor executor, MainThread mainThread, Context context) {
        super(executor, mainThread);
        this.context = context;
    }

    @Override
    protected void startProcess() {
        if (!error) {
            downloadVersions();
            startDownloadBlocks();
        } else {
            notifyError();
        }
    }

    private void startDownloadBlocks() {
        if (!error) {
            downloadAndSaveBlock1();
            finishDownload(ADConstants.LAST_UPDATE);
        } else {
            notifyError();
        }
    }

    //Series, Languages
    private void downloadAndSaveBlock1() {
        int MAX_BLOCK = 2;

        //One more to activity_game thread
        CountDownLatch lock = new CountDownLatch(MAX_BLOCK);

        //Serie
        checkSchema(new ADSerieSchema(), lock);
        checkSchema(new ADLanguageSchema(), lock);

        List<ADEntity> seriesDownloaded = getListSeriesDownloaded();

        //Download questions of series that have been updated
        for(ADEntity entity : seriesDownloaded){
            SyncronizeUpdateQuestionInteractor sincro = new SyncronizeUpdateQuestionInteractor(executor, mainThread, entity.getCode(), LanguageUtils.getLanguage());
            sincro.startProcess();
        }

        lock(lock);
        loadLanguage();
    }

    public List<ADEntity> getListSeriesDownloaded() {
        ADSerieSchema schema = new ADSerieSchema();
        ADRepository repository = new ADRepository(schema);

        List<ADEntity> entities = repository.fetchQuery(buildQuery(schema), null);


        return entities;
    }

    public RealmQuery<RealmObject> buildQuery(final ADSerieSchema schema) {
        RealmQuery<RealmObject> query;
        Realm realm = Realm.getDefaultInstance();

        query = realm.where(schema.getEntityDAO());
        query.equalTo(ADSerieSchema.COLUMN_ACTIVE, true);
        query.equalTo(ADSerieSchema.COLUMN_DOWNLOADED, true);

        return query;
    }
}
