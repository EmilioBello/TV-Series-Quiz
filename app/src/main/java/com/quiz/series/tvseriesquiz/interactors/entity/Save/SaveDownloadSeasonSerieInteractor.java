package com.quiz.series.tvseriesquiz.interactors.entity.Save;

import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.model.datastore.realm.ADRealm;
import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADSerie;

import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by Emilio on 17/08/2017.
 */

public class SaveDownloadSeasonSerieInteractor extends SaveEntityInteractor {

    private final int season;

    public SaveDownloadSeasonSerieInteractor(final Executor executor, final ADSchema schema, final int code, final int season){
        super(executor, schema, code);

        this.season = season;
    }

    @Override
    public ADEntity createEntity() {
        //Progress
        ADSchema schema = new ADSerieSchema();
        ADRepository repository = new ADRepository(schema);

        ADSerie serie = (ADSerie) repository.fetch(buildQuery());
        serie.setSeasonDownload(season);

        return serie;
    }


    public RealmQuery<RealmObject> buildQuery() {
        RealmQuery<RealmObject> query;
        Realm realm = Realm.getDefaultInstance();

        query = realm.where(schema.getEntityDAO());
        query.equalTo(ADSerieSchema.COLUMN_ACTIVE, true);
        query.equalTo(ADSerieSchema.COLUMN_CODE, code);

        return query;
    }
}
