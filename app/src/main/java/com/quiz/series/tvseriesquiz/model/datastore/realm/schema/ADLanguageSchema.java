package com.quiz.series.tvseriesquiz.model.datastore.realm.schema;


import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADLanguageDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADLanguageJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADLanguage;

import java.util.HashMap;
import java.util.List;

/**
 * ADProductSchema contain the schema for entity Product
 */
public class ADLanguageSchema implements ADSchema {
    public static final String DB_NAME_ONLINE     = "LANGUAGE";
    public static final String DB_NAME_OFFLINE    = "ADLanguageDAO";

    public static final String COLUMN_CODE             = "code";
    public static final String COLUMN_ALIAS            = "alias";
    public static final String COLUMN_VALUE            = "value";
    public static final String COLUMN_LANGUAGE         = "language";
    public static final String COLUMN_CREATED_AT       = "createdAt";
    public static final String COLUMN_UPDATED_AT       = "updatedAt";


    public static final String[] LANGUAGE_ALLCOLUMNS = {
            COLUMN_CODE,
            COLUMN_ALIAS,
            COLUMN_VALUE,
            COLUMN_LANGUAGE,
            COLUMN_CREATED_AT,
            COLUMN_UPDATED_AT
    };

    private HashMap<String, ADSchemaType> types;
    public static final ADSchemaType[] LANGUAGE_ALLCOLUMNS_TYPES = {
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER
    };

    public ADLanguageSchema() {
        types =  new HashMap<String, ADSchemaType>();

        for (int i=0; i<LANGUAGE_ALLCOLUMNS.length; ++i) {
            types.put(LANGUAGE_ALLCOLUMNS[i], LANGUAGE_ALLCOLUMNS_TYPES[i]);
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
        return ADLanguage.class;
    }

    @Override
    public Class getEntityDAO() {
        return ADLanguageDAO.class;
    }

    @Override
    public Class getEntityJSON() {
        return ADLanguageJSON.class;
    }

    @Override
    public ADSchemaType getType(String field) {
        return types.get(field);
    }

    @Override
    public String getOrderDefault() {
        return COLUMN_CODE;
    }
}
