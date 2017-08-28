package com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 28/06/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADQuestionJSON extends ADEntityJSON{

    public ADQuestionJSON(){}

    //ADEntity
    private int code;
    private int active;
    private long updatedAt;

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;
    private int season;
    private int episode;
    private String language;
    private int serie_code;
}
