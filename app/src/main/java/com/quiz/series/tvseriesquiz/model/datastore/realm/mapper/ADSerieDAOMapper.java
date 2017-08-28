package com.quiz.series.tvseriesquiz.model.datastore.realm.mapper;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADSerie;
import com.quiz.series.tvseriesquiz.util.StringUtils;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by Emilio on 12/03/2016.
 */
public class ADSerieDAOMapper implements ADDAOMapper {

    @Override
    public ADEntity convertDAOToEntity(RealmObject object) {

        ADSerie entity = ADSerie.builder().build();
        ADSerieDAO dao = (ADSerieDAO) object;

        //ADEntity
        entity.setCode(dao.getCode());
        entity.setActive(dao.isActive());
        entity.setUpdatedAt(dao.getUpdatedAt());
        entity.setCreatedAt(dao.getCreatedAt());

        //ADSerie
        entity.setName(dao.getName());
        entity.setSeason(dao.getSeasons());
        entity.setTotalEpisodes(dao.getTotalEpisodes());

        entity.setEpisodeProgress(dao.getEpisodeProgress());
        entity.setSeasonProgress(dao.getSeasonProgress());

        entity.setSeasonDownload(dao.getSeasonDownload());

        if(!StringUtils.isNullOrEmpty(dao.getListEpisode())){
            ArrayList<Integer> list = new ArrayList<>();

            String listEpisode = dao.getListEpisode();
            String[] array = listEpisode.split(",");

            for(String number : array){
                list.add(Integer.valueOf(number));
            }

            entity.setListEpisode(list);
        }

        entity.setComplete(dao.isComplete());
        entity.setStatus(dao.getStatus());

        entity.setUrlAvatar(dao.getUrlAvatar());
        entity.setUrlImageBackground(dao.getUrlImageBackground());

        return entity;
    }

    @Override
    public RealmObject convertEntityToDAO(ADEntity entity) {
        ADSerie serie = (ADSerie)entity;
        ADSerieDAO dao = new ADSerieDAO();

        //ADEntity
        dao.setCode(serie.getCode());
        dao.setActive(serie.isActive());
        dao.setUpdatedAt(serie.getUpdatedAt());
        dao.setCreatedAt(serie.getCreatedAt());

        //ADSerie
        dao.setName(serie.getName());
        dao.setSeasons(serie.getSeason());
        dao.setTotalEpisodes(serie.getTotalEpisodes());

        dao.setEpisodeProgress(serie.getEpisodeProgress());
        dao.setSeasonProgress(serie.getSeasonProgress());

        dao.setSeasonDownload(serie.getSeasonDownload());

        dao.setComplete(serie.isComplete());
        dao.setStatus(serie.getStatus());

        dao.setUrlAvatar(serie.getUrlAvatar());
        dao.setUrlImageBackground(serie.getUrlImageBackground());

        return dao;
    }
}
