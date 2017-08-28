package com.quiz.series.tvseriesquiz.model.datastore.realm.schema;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADVersionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADVersionJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADVersion;

import java.util.HashMap;
import java.util.List;


public class ADVersionSchema implements ADSchema {

    public static final String DB_NAME_ONLINE     = "VERSION";
    public static final String DB_NAME_OFFLINE    = "ADVersionDAO";

    //ADEntity
    public static final String COLUMN_OBJECTID    = "objectId";
    public static final String COLUMN_CODE        = "code";
    public static final String COLUMN_ACTIVE      = "active";
    public static final String COLUMN_CREATED_AT  = "createdAt";
    public static final String COLUMN_UPDATED_AT  = "updatedAt";

    //ADVersionDAO
    public static final String COLUMN_NAME        = "name";
    public static final String COLUMN_VERSION     = "version";


    private HashMap<String, ADSchemaType> types;
    public static final String[] VERSION_ALLCOLUMNS = {
            COLUMN_OBJECTID,
            COLUMN_CODE,
            COLUMN_VERSION,
            COLUMN_ACTIVE,
            COLUMN_CREATED_AT,
            COLUMN_UPDATED_AT,
            COLUMN_NAME,
    };

    public static final ADSchemaType[] VERSION_ALLCOLUMNS_TYPES = {
            ADSchemaType.TEXT,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.TEXT
    };

    public ADVersionSchema() {
        types =  new HashMap<String, ADSchemaType>();

        for (int i=0; i<VERSION_ALLCOLUMNS.length; ++i) {
            types.put(VERSION_ALLCOLUMNS[i], VERSION_ALLCOLUMNS_TYPES[i]);
        }
    }

    @Override
    public String getNameDBOnline() {
        return DB_NAME_ONLINE;
    }

    @Override
    public String getNameDBOffline() {
        return DB_NAME_OFFLINE;
    }

    @Override
    public Class getEntity() {
        return ADVersion.class;
    }

    @Override
    public Class getEntityDAO() {
        return ADVersionDAO.class;
    }

    @Override
    public Class getEntityJSON() {
        return ADVersionJSON.class;
    }

    @Override
    public String getOrderDefault() {
        return COLUMN_NAME;
    }

    @Override
    public ADSchemaType getType(String field) {
        return types.get(field);
    }
}
