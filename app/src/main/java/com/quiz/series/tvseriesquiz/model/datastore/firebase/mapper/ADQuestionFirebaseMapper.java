package com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADEntityJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADQuestionJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.util.DateUtils;
import com.quiz.series.tvseriesquiz.util.GeneralUtils;

import java.util.Map;

import io.realm.RealmObject;

/**
 * Created by Emilio on 28/06/2016.
 */

public class ADQuestionFirebaseMapper implements ADFirebaseMapper {
    @Override
    public RealmObject convertFirebaseObjectToDAO(ADEntityJSON object) {
        ADQuestionJSON json = (ADQuestionJSON)object;
        ADQuestionDAO dao = new ADQuestionDAO();

        //ADEntityDAO
        dao.setCode((json.getSerie_code() * 10000) + json.getCode()); //Because every single question need to have a different code
        dao.setActive(GeneralUtils.intToBoolean(json.getActive()));

        dao.setUpdatedAt(DateUtils.convertLongToDate(json.getUpdatedAt()));

        dao.setQuestion(json.getQuestion());
        dao.setAnswer1(json.getAnswer1());
        dao.setAnswer2(json.getAnswer2());
        dao.setAnswer3(json.getAnswer3());
        dao.setAnswer4(json.getAnswer4());
        dao.setCorrect(json.getCorrect());
        dao.setSeason(json.getSeason());
        dao.setEpisode(json.getEpisode());
        dao.setLanguage(json.getLanguage());
        dao.setSerie_code(json.getSerie_code());

        return dao;
    }

    @Override
    public Map<String, Object> convertEntityToFirebaseObject(ADEntity entity) {
        return null;
    }
}
