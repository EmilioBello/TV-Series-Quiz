package com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by Emilio on 28/06/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADEntityJSON {

    public ADEntityJSON() {
        // empty default constructor, necessary for Firebase to be able to deserialize
    }
}
