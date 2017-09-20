package com.quiz.series.tvseriesquiz;

import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADSerieDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO.ADVersionDAO;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADQuestionSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xoker on 09/08/2015.
 */
public class ADConstants {

    //General Constants
    public static final String APPNAME = "TV Series Quiz";


    //Image and Resolution
    public static final String IMAGE1X_64               = "image1x";
    public static final String IMAGE15X_64              = "image15x";
    public static final String IMAGE2X_64               = "image2x";
    public static final String IMAGE3X_64               = "image3x";

    public static final String IMAGE1X                  = "urlAvatar1x";
    public static final String IMAGE15X                 = "urlAvatar15x";
    public static final String IMAGE2X                  = "urlAvatar2x";
    public static final String IMAGE3X                  = "urlAvatar3x";

    public static final String MINIATURE1X              = "urlBackgroundMiniature1x";
    public static final String MINIATURE15X             = "urlBackgroundMiniature15x";
    public static final String MINIATURE2X              = "urlBackgroundMiniature2x";
    public static final String MINIATURE3X              = "urlBackgroundMiniature3x";

    public static final String IMAGE1X_VALUE            = "1.0";
    public static final String IMAGE15X_VALUE           = "1.5";
    public static final String IMAGE2X_VALUE            = "2.0";
    public static final String IMAGE3X_VALUE            = "3.0";

    //Other
    public static final String LAST_UPDATE              = "LAST_UPDATE";

    //DATABASE CLASS TO CLEAR
    public static final List<Class> listClearDB = Collections.unmodifiableList(
            new ArrayList<Class>() {{
                add(ADSerieDAO.class);
                add(ADQuestion.class);
                add(ADVersionDAO.class);
            }});

    public static final List<String> listDB = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(ADSerieSchema.DB_NAME_ONLINE);
                add(ADQuestionSchema.DB_NAME_ONLINE);
            }});

    public static final String getNameSerie(final int code){
        String name = "";

        if(code == 1){
            name = "GOT";
        }
        else if(code == 2){
            name = "BRB";
        }

        return name;
    }
}
