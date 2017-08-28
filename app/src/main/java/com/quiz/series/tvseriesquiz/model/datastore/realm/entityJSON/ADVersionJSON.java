package com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 28/06/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADVersionJSON extends ADEntityJSON{

    public ADVersionJSON(){}

    //ADEntity
    private int code;
    private boolean active;
    private long updatedAt;

    private String name;
    private int version;
}
