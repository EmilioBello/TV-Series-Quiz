package com.quiz.series.tvseriesquiz.model.datastore.realm.mapper;


import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import io.realm.RealmObject;

/**
 * Created by Emilio on 16/10/2015.
 */
public interface ADDAOMapper {
    public ADEntity convertDAOToEntity(RealmObject object);
    public RealmObject convertEntityToDAO(ADEntity entity);
}
