package com.quiz.series.tvseriesquiz.interactors.entities;

import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.realm.ADRealm;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.List;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;


public class GetQuestionBySerieLanguageAndEpisodeInteractor extends GetEntitiesInteractor {

    private int season, episode, serieCode;
    private String language;

    public GetQuestionBySerieLanguageAndEpisodeInteractor(Executor executor, MainThread mainThread, int season, int episode, int serieCode, String language) {
        super(executor, mainThread, new ADQuestionSchema());

        this.season = season;
        this.episode = episode;
        this.serieCode = serieCode;
        this.language = language;
    }

    @Override
    public String getOrder() {
        return schema.getOrderDefault();
    }

    @Override
    public RealmQuery<RealmObject> buildQuery() {
        RealmQuery<RealmObject> query;
        Realm realm = Realm.getDefaultInstance();

        query = realm.where(schema.getEntityDAO());
        //query.equalTo(ADQuestionSchema.COLUMN_ACTIVE, true);
        query.equalTo(ADQuestionSchema.COLUMN_SERIECODE, serieCode);
        query.equalTo(ADQuestionSchema.COLUMN_SEASON, season);
        query.equalTo(ADQuestionSchema.COLUMN_EPISODE, episode);
        query.equalTo(ADQuestionSchema.COLUMN_LANGUAGE, language);

        return query;
    }

    @Override
    public List<ADEntity> processResult(List<ADEntity> entities) {
        return entities;
    }
}
