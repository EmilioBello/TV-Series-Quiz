package com.quiz.series.tvseriesquiz.model.datastore.realm.repository;

/**
 * Created by Emilio on 29/06/2016.
 */

import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmQuery;


public interface ADRepositoryInterface {
    void save(ADEntity entity);
    void save(List<ADEntity> entities);
    void updateValues(List<ADEntity> entities);
    void updateValues(ADEntity entity);

    List<ADEntity> fetchAll(RealmQuery<RealmObject> query, String sort);
    ADEntity fetch(RealmQuery<RealmObject> query);
    List<ADEntity> fetchQuery(RealmQuery<RealmObject> query, String sort);

    //DAO
    RealmObject fetchDAO(RealmQuery<RealmObject> query);
    List<RealmObject> fetchAllDAO(RealmQuery<RealmObject> query, String sort);
    List<RealmObject> fetchQueryDAO(RealmQuery<RealmObject> query, String sort);
    void saveDAO(List<RealmObject> daos);
}
