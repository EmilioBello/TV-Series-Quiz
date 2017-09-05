package com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.model.datastore.realm.ADRealm;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADEntityJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADSerieJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADSerie;
import com.quiz.series.tvseriesquiz.util.DateUtils;
import com.quiz.series.tvseriesquiz.util.DisplayUtils;
import com.quiz.series.tvseriesquiz.util.GeneralUtils;
import com.quiz.series.tvseriesquiz.util.StringUtils;

import android.content.Context;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by Emilio on 28/06/2016.
 */

public class ADSerieFirebaseMapper implements ADFirebaseMapper {

    private Context context;

    public ADSerieFirebaseMapper(Context context) {
        this.context = context;
    }

    public ADSerieFirebaseMapper() {
        this.context = MyApp.getContext();
    }

    @Override
    public RealmObject convertFirebaseObjectToDAO(ADEntityJSON object) {

        ADSerieJSON json = (ADSerieJSON)object;
        ADSerieDAO dao = new ADSerieDAO();

        //ADEntityDAO
        dao.setCode(json.getCode());
        dao.setActive(json.isActive());

        dao.setUpdatedAt(DateUtils.convertLongToDate(json.getUpdatedAt()));

        dao.setName(json.getName());
        dao.setSeasons(json.getSeasons());
        dao.setTotalEpisodes(json.getTotalEpisodes());

        //Progress
        ADSchema schema = new ADSerieSchema();
        ADRepository repository = new ADRepository(schema);

        ADSerieDAO serie = (ADSerieDAO)  repository.fetchDAO(buildQuery(schema, dao));

        if(serie != null){
            dao.setEpisodeProgress(serie.getEpisodeProgress());
            dao.setSeasonProgress(serie.getSeasonProgress());
        }
        else{
            dao.setEpisodeProgress(json.getEpisodeProgress());
            dao.setSeasonProgress(json.getSeasonProgress());
        }

        dao.setListEpisode(json.getListEpisode());

        dao.setComplete(json.isComplete());
        dao.setStatus(json.getStatus());

        //check resolution image
        String resolution = DisplayUtils.getDensity(context);

        if (ADConstants.IMAGE1X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar1x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar1x());
                dao.setUrlImageBackground(json.getUrlImageBackground1x());
            }
        } else if(ADConstants.IMAGE15X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar15x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar15x());
                dao.setUrlImageBackground(json.getUrlImageBackground15x());
            }
        } else if(ADConstants.IMAGE2X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar2x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar2x());
                dao.setUrlImageBackground(json.getUrlImageBackground2x());
            }
        } else if(ADConstants.IMAGE3X_VALUE.equals(resolution)) {
            if (json.getUrlAvatar3x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar3x());
                dao.setUrlImageBackground(json.getUrlImageBackground3x());
            }
        } else if(resolution.startsWith("0")) {
            if (json.getUrlAvatar1x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar1x());
                dao.setUrlImageBackground(json.getUrlImageBackground1x());
            }
        } else {
            if (json.getUrlAvatar3x() != null) {
                dao.setUrlAvatar(json.getUrlAvatar3x());
                dao.setUrlImageBackground(json.getUrlImageBackground3x());
            }
        }

        return dao;
    }

    @Override
    public Map<String, Object> convertEntityToFirebaseObject(ADEntity entity) {
        return null;
    }

    private RealmQuery<RealmObject> buildQuery(ADSchema schema, ADSerieDAO dao) {
        RealmQuery<RealmObject> query;

        Realm realm = Realm.getDefaultInstance();
        query = realm.where(schema.getEntityDAO());
        query.equalTo(ADQuestionSchema.COLUMN_ACTIVE, true);
        query.equalTo(ADSerieSchema.COLUMN_CODE, dao.getCode());

        return query;
    }
}
