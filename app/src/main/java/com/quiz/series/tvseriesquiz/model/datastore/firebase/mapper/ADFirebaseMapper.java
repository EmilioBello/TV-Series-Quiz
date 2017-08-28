package com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADEntityJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.Map;

import io.realm.RealmObject;

/**
 * Created by Emilio on 28/06/2016.
 */

public interface ADFirebaseMapper {
    RealmObject convertFirebaseObjectToDAO(ADEntityJSON object);
    Map<String, Object> convertEntityToFirebaseObject(ADEntity entity);
}
