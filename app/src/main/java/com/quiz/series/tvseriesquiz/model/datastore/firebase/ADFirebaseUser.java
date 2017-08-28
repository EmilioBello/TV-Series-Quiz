package com.quiz.series.tvseriesquiz.model.datastore.firebase;

/**
 * Created by Emilio on 30/06/2016.
 */
import com.google.firebase.auth.FirebaseUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ADFirebaseUser {

    public static final String EMAIL    = "app@tvseriesquiz.com";
    public static final String PASS     = "qdvF$!gJQe*3";

    public int group = 0;
    public FirebaseUser user = null;
}
