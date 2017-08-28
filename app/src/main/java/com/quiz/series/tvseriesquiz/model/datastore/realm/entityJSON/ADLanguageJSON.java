package com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON;

import com.quiz.series.tvseriesquiz.model.entity.ADLanguage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 10/10/2016.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ADLanguageJSON extends ADEntityJSON{

    public ADLanguageJSON(){}

    //ADEntity
    private int code;
    private long updatedAt;

    private String alias;
    private String value;
    private String language;
}
