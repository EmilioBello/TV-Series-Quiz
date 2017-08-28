package com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ADVersionDAO extends RealmObject {
    //ADEntityDAO
    @PrimaryKey
    private int code;

    private Date createdAt;
    private Date updatedAt;
    private boolean active;

    //ADVersion
    private String name;
    private int version;
}
