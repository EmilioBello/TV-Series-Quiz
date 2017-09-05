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

/**
 * Created by Emilio on 10/07/2016.
 */

public class GetSeriesInteractor extends GetEntitiesInteractor {

    public GetSeriesInteractor(Executor executor, MainThread mainThread) {
        super(executor, mainThread, new ADSerieSchema());
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
        query.equalTo(ADSerieSchema.COLUMN_ACTIVE, true);

        return query;
    }

    @Override
    public List<ADEntity> processResult(List<ADEntity> entities) {
        return entities;
    }
}
