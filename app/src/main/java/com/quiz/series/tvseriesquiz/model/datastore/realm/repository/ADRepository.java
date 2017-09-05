package com.quiz.series.tvseriesquiz.model.datastore.realm.repository;

/**
 * Created by Emilio on 29/06/2016.
 */

import android.content.Context;
import android.util.Log;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.model.datastore.realm.ADRealm;
import com.quiz.series.tvseriesquiz.model.datastore.realm.mapper.ADDAOMapper;
import com.quiz.series.tvseriesquiz.model.datastore.realm.mapper.ADLanguageDAOMapper;
import com.quiz.series.tvseriesquiz.model.datastore.realm.mapper.ADQuestionDAOMapper;
import com.quiz.series.tvseriesquiz.model.datastore.realm.mapper.ADSerieDAOMapper;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADLanguageSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;



public class ADRepository implements ADRepositoryInterface {

    private static final long REALM_SCHEMA_VERSION = 1;

    protected Context context;
    protected ADSchema schema;

    public ADRepository(final ADSchema schema) {
        this(schema, MyApp.getContext());
    }

    public ADRepository(final ADSchema schema, Context context) {
        this.schema = schema;
        this.context = context;
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    @Override
    public void save(ADEntity entity) {
        if(entity != null){
            Realm realm = getRealm();
            realm.beginTransaction();

            ADDAOMapper mapper = getDAOMapper();
            RealmObject dao = mapper.convertEntityToDAO(entity);
            if(dao != null){
                realm.insertOrUpdate(dao);
            }

            realm.commitTransaction();
            realm.close();
        }
    }

    @Override
    public void save(List<ADEntity> entities) {
        if(entities.size() > 0){
            Realm realm = getRealm();

            RealmList<RealmObject> entitiesDAO = new RealmList<>();
            for (ADEntity entity : entities) {
                ADDAOMapper mapper = getDAOMapper();
                RealmObject dao = mapper.convertEntityToDAO(entity);
                if(dao != null){
                    entitiesDAO.add(dao);
                }
            }

            if(!entitiesDAO.isEmpty()){
                realm.beginTransaction();
                realm.insertOrUpdate(entitiesDAO);
                realm.commitTransaction();
            }


            realm.close();
        }
    }

    @Override
    public void updateValues(List<ADEntity> entities) {
        save(entities);
    }

    @Override
    public void updateValues(ADEntity entity) {
        save(entity);
    }

    @Override
    public List<ADEntity> fetchAll(RealmQuery<RealmObject> query, String sort) {
        return fetchQuery(query, sort);
    }

    @Override
    public ADEntity fetch(RealmQuery<RealmObject> query) {
        List<ADEntity> entities;
        entities = fetchQuery(query, null);

        if (entities.size() > 0) {
            return entities.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<ADEntity> fetchQuery(RealmQuery<RealmObject> query, String sort) {
        ADDAOMapper mapper = getDAOMapper();
        RealmResults<RealmObject> entitiesDAO = null;

        if (StringUtils.isNullOrEmpty(sort)){
            entitiesDAO = query.findAll();
        } else {
            entitiesDAO = query.findAllSorted(sort);
        }

        List<ADEntity> entities = new ArrayList<>();
        for(RealmObject entityDAO : entitiesDAO){
            entities.add(mapper.convertDAOToEntity(entityDAO));
        }

        return entities;
    }

    @Override
    public List<RealmObject> fetchAllDAO(RealmQuery<RealmObject> query, String sort) {
        return fetchQueryDAO(query, sort);
    }

    @Override
    public RealmObject fetchDAO(RealmQuery<RealmObject> query) {
        List<RealmObject> entitiesDAO = fetchQueryDAO(query, null);

        if (entitiesDAO.size() > 0) {
            return entitiesDAO.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<RealmObject> fetchQueryDAO(RealmQuery<RealmObject> query, String sort) {
        RealmResults<RealmObject> entitiesDAO = null;

        if(StringUtils.isNullOrEmpty(sort)){
            entitiesDAO = query.findAll();
        }
        else{
            entitiesDAO = query.findAllSorted(sort);
        }

        return entitiesDAO;
    }

    @Override
    public void saveDAO(List<RealmObject> daos) {
        if(daos.size() > 0){
            Realm realm = getRealm();

            if(!daos.isEmpty()){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(daos);
                realm.commitTransaction();
            }

            realm.close();
        }
    }


    public static void clearDB() {
        try
        { //Could be that Realm has not been created yet.
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            for(Class object : ADConstants.listClearDB){
                realm.delete(object);
            }
            realm.commitTransaction();
        } catch (Exception ex){
            Log.i(ADConstants.APPNAME, "ERROR" + ex.getLocalizedMessage());
        }
    }

    protected ADDAOMapper getDAOMapper() {
        if (schema instanceof ADSerieSchema){
            return new ADSerieDAOMapper();
        } else if(schema instanceof ADQuestionSchema){
            return new ADQuestionDAOMapper();
        } else if(schema instanceof ADLanguageSchema){
            return new ADLanguageDAOMapper();
        }else {
            return null;
        }
    }
}
