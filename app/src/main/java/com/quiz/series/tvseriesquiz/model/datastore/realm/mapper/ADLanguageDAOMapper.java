package com.quiz.series.tvseriesquiz.model.datastore.realm.mapper;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADLanguageDAO;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADLanguage;

import io.realm.RealmObject;

/**
 * Created by Emilio on 10/10/2016.
 */

public class ADLanguageDAOMapper implements ADDAOMapper {
    @Override
    public ADEntity convertDAOToEntity(RealmObject object) {
        ADLanguage entity = ADLanguage.builder().build();
        ADLanguageDAO dao = (ADLanguageDAO) object;

        //ADEntity
        entity.setCode(dao.getCode());
        entity.setUpdatedAt(dao.getUpdatedAt());
        entity.setCreatedAt(dao.getCreatedAt());

        entity.setAlias(dao.getAlias());
        entity.setValue(dao.getValue());
        entity.setLanguage(dao.getLanguage());

        return entity;
    }

    @Override
    public RealmObject convertEntityToDAO(ADEntity entity) {
        ADLanguage serie = (ADLanguage) entity;
        ADLanguageDAO dao = new ADLanguageDAO();

        //ADEntity
        dao.setCode(serie.getCode());
        dao.setUpdatedAt(serie.getUpdatedAt());
        dao.setCreatedAt(serie.getCreatedAt());

        dao.setAlias(serie.getAlias());
        dao.setValue(serie.getValue());
        dao.setLanguage(serie.getLanguage());

        return dao;
    }
}
