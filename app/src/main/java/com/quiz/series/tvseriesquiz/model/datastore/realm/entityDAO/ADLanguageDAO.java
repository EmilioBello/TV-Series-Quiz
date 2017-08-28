package com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 10/10/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADLanguageDAO extends RealmObject {

    //ADEntityDAO
    @PrimaryKey
    private int code;

    private Date createdAt;
    private Date updatedAt;

    private String alias;
    private String value;
    private String language;
}
