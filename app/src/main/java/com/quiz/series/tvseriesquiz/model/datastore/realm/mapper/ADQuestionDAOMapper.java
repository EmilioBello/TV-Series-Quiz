package com.quiz.series.tvseriesquiz.model.datastore.realm.mapper;


import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADQuestion;

import io.realm.RealmObject;

/**
 * Created by Emilio on 08/12/2015.
 */
public class ADQuestionDAOMapper implements ADDAOMapper {
    @Override
    public ADEntity convertDAOToEntity(RealmObject object) {
        ADQuestion entity = ADQuestion.builder().build();
        ADQuestionDAO dao = (ADQuestionDAO) object;

        //ADEntity
        entity.setCode(dao.getCode());
        entity.setActive(dao.isActive());
        entity.setUpdatedAt(dao.getUpdatedAt());

        //ADQuestion
        entity.setQuestion(dao.getQuestion());
        entity.setAnswer1(dao.getAnswer1());
        entity.setAnswer2(dao.getAnswer2());
        entity.setAnswer3(dao.getAnswer3());
        entity.setAnswer4(dao.getAnswer4());
        entity.setCorrect(dao.getCorrect());
        entity.setSeason(dao.getSeason());
        entity.setEpisode(dao.getEpisode());
        entity.setLanguage(dao.getLanguage());
        entity.setSerieCode(dao.getSerie_code());

        return entity;
    }

    @Override
    public RealmObject convertEntityToDAO(ADEntity entity) {
        ADQuestion question = (ADQuestion)entity;
        ADQuestionDAO dao = new ADQuestionDAO();

        //ADEntity
        dao.setCode(question.getCode());
        dao.setActive(question.isActive());
        dao.setUpdatedAt(question.getUpdatedAt());

        //ADQuestion
        dao.setQuestion(question.getQuestion());
        dao.setAnswer1(question.getAnswer1());
        dao.setAnswer2(question.getAnswer2());
        dao.setAnswer3(question.getAnswer3());
        dao.setAnswer4(question.getAnswer4());
        dao.setCorrect(question.getCorrect());
        dao.setSeason(question.getSeason());
        dao.setEpisode(question.getEpisode());
        dao.setLanguage(question.getLanguage());
        dao.setSerie_code(question.getSerieCode());

        return dao;
    }
}
