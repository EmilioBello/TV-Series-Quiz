package com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADLanguageDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADEntityJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADLanguageJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.util.DateUtils;
import com.quiz.series.tvseriesquiz.util.GeneralUtils;
import com.quiz.series.tvseriesquiz.util.StringUtils;

import java.util.Map;

import io.realm.RealmObject;

/**
 * Created by Emilio on 10/10/2016.
 */

public class ADLanguageFirebaseMapper implements ADFirebaseMapper {
    @Override
    public RealmObject convertFirebaseObjectToDAO(ADEntityJSON object) {
        ADLanguageJSON json = (ADLanguageJSON)object;
        ADLanguageDAO dao = new ADLanguageDAO();

        //ADEntityDAO
        dao.setCode(json.getCode());
        dao.setUpdatedAt(DateUtils.convertLongToDate(json.getUpdatedAt()));

        dao.setAlias(json.getAlias());
        dao.setValue(json.getValue());
        dao.setLanguage(json.getLanguage());

        return dao;
    }

    @Override
    public Map<String, Object> convertEntityToFirebaseObject(ADEntity entity) {
        return null;
    }
}
