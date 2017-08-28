package com.quiz.series.tvseriesquiz.interactors.entities;


import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.realm.ADRealm;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADLanguageSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.util.LanguageUtils;
import com.quiz.series.tvseriesquiz.util.StringUtils;

import java.util.List;
import java.util.concurrent.Executor;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by Emilio on 06/10/2015.
 */
public class GetLanguagesInteractor extends GetEntitiesInteractor {

    public GetLanguagesInteractor(Executor executor, MainThread mainThread) {
        super(executor, mainThread, new ADLanguageSchema());
    }

    @Override
    public String getOrder() {
        return schema.getOrderDefault();
    }

    @Override
    public RealmQuery<RealmObject> buildQuery() {
        RealmQuery<RealmObject> query;
        ADRealm adRealm = new ADRealm(MyApp.getContext());
        Realm realm = Realm.getInstance(adRealm.getConfig());

        //locale
        String language = LanguageUtils.getLanguage();

        if(StringUtils.isNullOrEmpty(language)){
            language = "en"; //Default Language
        }

        if(language != "en" || language != "es"){
            language = "en";
        }

        query = realm.where(schema.getEntityDAO());
        query.contains(ADLanguageSchema.COLUMN_LANGUAGE, language);

        return query;
    }

    @Override
    public List<ADEntity> processResult(List<ADEntity> entities) {
        return entities;
    }
}
