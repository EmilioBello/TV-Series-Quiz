package com.quiz.series.tvseriesquiz.model.datastore.firebase;

/**
 * Created by Emilio on 29/06/2016.
 */

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper.ADFirebaseMapper;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper.ADLanguageFirebaseMapper;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper.ADQuestionFirebaseMapper;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper.ADSerieFirebaseMapper;
import com.quiz.series.tvseriesquiz.model.datastore.firebase.mapper.ADVersionFirebaseMapper;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADEntityJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADVersionJSON;
import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADLanguageSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADVersionSchema;
import com.quiz.series.tvseriesquiz.util.DateUtils;
import com.quiz.series.tvseriesquiz.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import io.realm.RealmObject;
import io.realm.exceptions.RealmIOException;

public class ADFirebase implements ADFirebaseInterface, Runnable{

    public static DatabaseReference rootRef;

    private Long lastUpdatedAt; //lazy getter
    private ADSchema schema;
    private Context context;

    private static Vector<RealmObject> daos;
    private static ArrayList<DataSnapshot> dataFromFB;

    private final int start, end;

    public ADFirebase(ADSchema schema) {
        this.schema = schema;
        this.context = MyApp.getContext();

        start = 0;
        end = 0;

        if (rootRef == null) {
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            ADFirebase.rootRef = FirebaseDatabase.getInstance().getReference();
        }
    }

    public ADFirebase(ADSchema schema, Context context) {
        this.schema = schema;
        this.context = context;

        start = 0;
        end = 0;

        if (rootRef == null) {
            //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            ADFirebase.rootRef = FirebaseDatabase.getInstance().getReference();
        }
    }

    public ADFirebase(final int start, final int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public void downloader(final ADFirebase.Callback callback) {
        Query childRef = rootRef
                .child(schema.getNameDBOnline() + "/")
                .orderByChild(ADVersionSchema.COLUMN_UPDATED_AT)
                .startAt(getlastUpdatedAt());

        downloadAndSave(callback, childRef);
    }

    @Override
    public void downloader(int codeSerie, String language, final ADFirebase.Callback callback) {
        DatabaseReference childRef = rootRef
                .child(schema.getNameDBOnline() + "/")
                .child(String.valueOf(codeSerie))
                .child(language);

        downloadAndSave(callback, childRef);
    }

    @Override
    public void downloaderUpdateQuestion(final int codeSerie, final String language, final long update, final ADFirebase.Callback callback) {
        ADQuestionSchema questionSchema = (ADQuestionSchema) schema;
        Query childRef = rootRef.child(schema.getNameDBOnline() + "/")
                .child(String.valueOf(codeSerie))
                .child(language)
                .orderByChild(questionSchema.COLUMN_UPDATED_AT)
                .startAt(update);

        downloadAndSave(callback, childRef);
    }

    @Override
    public void downloader(final int codeSerie, final String language, final int season, final ADFirebase.Callback callback) {
        ADQuestionSchema questionSchema = (ADQuestionSchema) schema;
        Query childRef = rootRef.child(schema.getNameDBOnline() + "/")
                .child(String.valueOf(codeSerie))
                .child(language)
                .orderByChild(questionSchema.COLUMN_SEASON)
                .equalTo(season);

        downloadAndSave(callback, childRef);
    }

    private void downloadAndSave(final Callback callback, Query childRef) {

        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
        //childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ADFirebaseMapper mapper = getFirebaseMapper();
                daos = new Vector<>();
                dataFromFB = new ArrayList<>();

                //To improve performance parallelize this loop
                for (DataSnapshot entitySnapshot : dataSnapshot.getChildren()) {
                    dataFromFB.add(entitySnapshot);
                }

                if(dataFromFB.size() > 20) {
                    Thread[] threads = new Thread[4];

                    final int range = dataFromFB.size()/4; //becaouse it is going to be launched 4 threads always

                    threads[0] = new Thread(new ADFirebase(start, range));
                    threads[1] = new Thread(new ADFirebase(range, range*2));
                    threads[2] = new Thread(new ADFirebase(range*2, range*3));
                    threads[3] = new Thread(new ADFirebase(range*3, dataFromFB.size()));

                    for(int i = 0; i < threads.length; i++){
                        threads[i].start();
                    }

                    for(int i = 0; i < threads.length; i++){
                        try {
                            threads[i].join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    ArrayList<RealmObject> daosCached = new ArrayList<>();

                    for(DataSnapshot entitySnapshot : dataFromFB){
                        RealmObject dao = mapperJsonToDao(mapper, entitySnapshot);
                        daosCached.add(dao);
                    }

                    daos.addAll(daosCached);
                }
                syncronice(daos);

                if(schema instanceof ADQuestionSchema){
                    saveUpdateDateSharedPreference(daos);
                }

                callback.onSuccess();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    private RealmObject mapperJsonToDao(ADFirebaseMapper mapper, DataSnapshot entitySnapshot) {
        RealmObject dao = null;

        try {
            final ADEntityJSON json = (ADEntityJSON) entitySnapshot.getValue(schema.getEntityJSON());
            dao = mapper.convertFirebaseObjectToDAO(json);

        } catch (com.google.firebase.database.DatabaseException e) {
            Log.e(ADConstants.APPNAME, e.getLocalizedMessage());
        }

        return dao;
    }

    @Override
    public void run() {
        final ADFirebaseMapper mapper = getFirebaseMapper();
        ArrayList<RealmObject> daosCached = new ArrayList<>();

        for(int i = start; i < end; i++){
            RealmObject dao = mapperJsonToDao(mapper, dataFromFB.get(i));
            daosCached.add(dao);
        }

        daos.addAll(daosCached);
    }

    private void saveUpdateDateSharedPreference(List<RealmObject> daos) {
        ADQuestionDAO max = (ADQuestionDAO) Collections.max(daos, new Comparator<RealmObject>() {
            @Override
            public int compare(RealmObject o1, RealmObject o2) {
                ADQuestionDAO dao1 = (ADQuestionDAO) o1;
                ADQuestionDAO dao2 = (ADQuestionDAO) o2;
                if (dao1.getUpdatedAt().after(dao2.getUpdatedAt())){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });
        long date = DateUtils.convertDateToLong(max.getUpdatedAt());
        SharedPreferencesUtils.saveDate(context, ADConstants.gameOfThrones, date);
    }


    public void downloaderVersion(final ADFirebase.CallbackVersion callback) {
        DatabaseReference childRef = rootRef.child(ADVersionSchema.DB_NAME_ONLINE + "/");

        childRef.orderByChild(ADVersionSchema.COLUMN_UPDATED_AT).startAt(getlastUpdatedAt()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashSet<String> versions = new HashSet<>();

                for (DataSnapshot entitySnapshot : dataSnapshot.getChildren()) {
                    ADVersionJSON version = entitySnapshot.getValue(ADVersionJSON.class);
                    versions.add(version.getName());
                }

                callback.onSuccess(versions);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }


    /*public void downloaderConfig(final ADFirebase.CallbackConfig callback) {
        DatabaseReference childRef = rootRef.child(ADConfig.DB_NAME_ONLINE + "/");

        childRef.orderByChild(ADConfigSchema.COLUMN_UPDATED_AT).startAt(getlastUpdatedAt()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> configs = new HashMap<String, String>();

                for (DataSnapshot entitySnapshot: dataSnapshot.getChildren()) {
                    ADConfigJSON config = entitySnapshot.getValue(ADConfigJSON.class);
                    configs.put(config.getName(), config.getValue());
                }

                //check and process snapshot
                /*
                if (dataSnapshot.hasChildren()) {
                    ADFirebaseMapper mapper = new ADConfigFirebaseMapper();

                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    DataSnapshot snapshotChildren;

                    while (iterator.hasNext()) {
                        snapshotChildren = iterator.next();

                        Date snapshotUpdatedAt = snapshotChildren.child(UPDATEDAT).getValue(Date.class);
                        if (snapshotUpdatedAt.after(getlastUpdatedAt()) || schema instanceof ADConfigSchema) {
                            String name = snapshotChildren.child(ADVersionSchema.COLUMN_NAME).getValue(String.class);
                            String value = snapshotChildren.child(ADVersionSchema.COLUMN_VALUE).getValue(String.class);

                            configs.put(name, value);
                        }
                    }
                }
                *//*

                callback.onSuccess(configs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }*/


    public void syncronice(List<RealmObject> daos) {
        ADRepository repository = new ADRepository(schema);
        repository.saveDAO(daos);
    }

    public void update(final String database, final String path, final Map<String, Object> data, final ADFirebase.Callback callback) {
        DatabaseReference childRef = rootRef.child(path);

        childRef.updateChildren(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            if (databaseError == null) {

                //update version
                DatabaseReference versionRef = rootRef.child(ADVersionSchema.DB_NAME_ONLINE + "/" + database);
                Map<String, Object> versionData = new HashMap<String, Object>();

                Date now = new Date();
                versionData.put(ADVersionSchema.COLUMN_UPDATED_AT, now.getTime());

                versionRef.updateChildren(versionData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseVersionError, DatabaseReference databaseReference) {
                        if (databaseVersionError == null) {
                            callback.onSuccess();
                        } else {
                            callback.onError(databaseVersionError.getMessage());
                        }
                    }
                });
            } else {
                callback.onError(databaseError.getMessage());
            }
            }
        });

    }


    protected ADFirebaseMapper getFirebaseMapper() {
        if(schema instanceof ADSerieSchema) {
            return new ADSerieFirebaseMapper();
        } else if(schema instanceof ADQuestionSchema) {
            return new ADQuestionFirebaseMapper();
        } else if(schema instanceof ADVersionSchema) {
            return new ADVersionFirebaseMapper();
         }else if(schema instanceof ADLanguageSchema) {
            return new ADLanguageFirebaseMapper();
        } else {
            return null;
        }
    }


    //lazy getter
    private Long getlastUpdatedAt() {
        if (lastUpdatedAt == null) {
            lastUpdatedAt = SharedPreferencesUtils.getDate(ADConstants.LAST_UPDATE, context);
        }

        return lastUpdatedAt;
    }
}
