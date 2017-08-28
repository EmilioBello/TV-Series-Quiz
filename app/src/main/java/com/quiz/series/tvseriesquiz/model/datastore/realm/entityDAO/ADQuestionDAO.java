package com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 15/02/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADQuestionDAO extends RealmObject {

    //ADEntityDAO
    @PrimaryKey
    private int code;

    private Date updatedAt;
    private boolean active;

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;
    private int season;
    private int episode;

    @Index
    private String language;

    @Index
    private int serie_code;
}
