package com.quiz.series.tvseriesquiz.model.datastore.realm.schema;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADVersionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADSerieJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADSerie;
import com.quiz.series.tvseriesquiz.model.entity.ADVersion;

import java.util.HashMap;

/**
 * Created by Emilio on 12/03/2016.
 */
public class ADSerieSchema implements ADSchema {

    public static final String DB_NAME_ONLINE       = "SERIE";
    public static final String DB_NAME_OFFLINE      = "ADSerieDAO";

    //ADEntity
    public static final String COLUMN_CODE          = "code";
    public static final String COLUMN_ACTIVE        = "active";
    public static final String COLUMN_CREATED_AT    = "createdAt";
    public static final String COLUMN_UPDATED_AT    = "updatedAt";

    //ADQuestion
    public static final String COLUMN_NAME          = "name";
    public static final String COLUMN_SEASON        = "seasons";
    public static final String COLUMN_TOTALEPISODES = "totalEpisodes";
    public static final String COLUMN_URLIMAGE1X    = "urlAvatar1x";
    public static final String COLUMN_URLIMAGE15X   = "urlAvatar15x";
    public static final String COLUMN_URLIMAGE2X    = "urlAvatar2x";
    public static final String COLUMN_URLIMAGE3X    = "urlAvatar3x";

    private HashMap<String, ADSchemaType> types;
    public static final String[] VERSION_ALLCOLUMNS = {
            COLUMN_CODE,
            COLUMN_ACTIVE,
            COLUMN_CREATED_AT,
            COLUMN_UPDATED_AT,
            COLUMN_NAME,
            COLUMN_SEASON,
            COLUMN_TOTALEPISODES,
            COLUMN_URLIMAGE1X,
            COLUMN_URLIMAGE15X,
            COLUMN_URLIMAGE2X,
            COLUMN_URLIMAGE3X
    };

    public static final ADSchemaType[] VERSION_ALLCOLUMNS_TYPES = {
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,

            ADSchemaType.TEXT,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT
    };

    public ADSerieSchema() {
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
        return ADSerie.class;
    }

    @Override
    public Class getEntityDAO() {
        return ADSerieDAO.class;
    }

    @Override
    public Class getEntityJSON() {
        return ADSerieJSON.class;
    }

    @Override
    public String getOrderDefault() {
        return COLUMN_CODE;
    }

    @Override
    public ADSchemaType getType(String field) {
        return types.get(field);
    }
}
