package com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADVersionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADEntityJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADVersionJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.util.DateUtils;
import com.quiz.series.tvseriesquiz.util.GeneralUtils;
import com.quiz.series.tvseriesquiz.util.StringUtils;

import java.util.Map;

import io.realm.RealmObject;

/**
 * Created by Emilio on 28/06/2016.
 */

public class ADVersionFirebaseMapper implements ADFirebaseMapper {
    @Override
    public RealmObject convertFirebaseObjectToDAO(ADEntityJSON object) {
        ADVersionJSON json = (ADVersionJSON)object;
        ADVersionDAO dao = new ADVersionDAO();

        //ADEntityDAO
        dao.setCode(json.getCode());
        dao.setActive(json.isActive());

        dao.setUpdatedAt(DateUtils.convertLongToDate(json.getUpdatedAt()));

        dao.setName(json.getName());
        dao.setVersion(json.getVersion());

        return dao;
    }

    @Override
    public Map<String, Object> convertEntityToFirebaseObject(ADEntity entity) {
        return null;
    }
}
