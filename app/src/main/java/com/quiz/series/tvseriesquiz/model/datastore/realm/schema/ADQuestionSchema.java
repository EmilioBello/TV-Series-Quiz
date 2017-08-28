package com.quiz.series.tvseriesquiz.model.datastore.realm.schema;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADQuestionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON.ADQuestionJSON;
import com.quiz.series.tvseriesquiz.model.entity.ADQuestion;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Emilio on 12/03/2016.
 */
public class ADQuestionSchema implements ADSchema {

    public static final String DB_NAME_ONLINE       = "QUESTION";
    public static final String DB_NAME_OFFLINE      = "ADQuestionDAO";

    //ADEntity
    public static final String COLUMN_CODE          = "code";
    public static final String COLUMN_ACTIVE        = "active";
    public static final String COLUMN_CREATED_AT    = "createdAt";
    public static final String COLUMN_UPDATED_AT    = "updatedAt";

    //ADQuestion
    public static final String COLUMN_QUESTION      = "question";
    public static final String COLUMN_ANSWER1       = "answer1";
    public static final String COLUMN_ANSWER2       = "answer2";
    public static final String COLUMN_ANSWER3       = "answer3";
    public static final String COLUMN_ANSWER4       = "answer4";
    public static final String COLUMN_CORRECT       = "correct";
    public static final String COLUMN_SEASON        = "season";
    public static final String COLUMN_EPISODE       = "episode";
    public static final String COLUMN_LANGUAGE      = "language";
    public static final String COLUMN_SERIECODE     = "serie_code";

    private HashMap<String, ADSchemaType> types;
    public static final String[] VERSION_ALLCOLUMNS = {
            COLUMN_CODE,
            COLUMN_ACTIVE,
            COLUMN_CREATED_AT,
            COLUMN_UPDATED_AT,
            COLUMN_QUESTION,
            COLUMN_ANSWER1,
            COLUMN_ANSWER2,
            COLUMN_ANSWER3,
            COLUMN_ANSWER4,
            COLUMN_CORRECT,
            COLUMN_SEASON,
            COLUMN_EPISODE,
            COLUMN_LANGUAGE,
            COLUMN_SERIECODE
    };

    public static final ADSchemaType[] VERSION_ALLCOLUMNS_TYPES = {
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.TEXT,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.INTEGER,
            ADSchemaType.TEXT,
            ADSchemaType.INTEGER
    };

    public ADQuestionSchema() {
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
        return ADQuestion.class;
    }

    @Override
    public Class getEntityDAO() {
        return ADQuestionDAO.class;
    }

    @Override
    public Class getEntityJSON() {
        return ADQuestionJSON.class;
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
