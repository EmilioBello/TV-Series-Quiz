package com.quiz.series.tvseriesquiz.model.datastore.firebase;

/**
 * Created by Emilio on 30/06/2016.
 */

public interface ADFirebaseAuthInterface {
    void authAnonymously(ADFirebaseAuth.Callback callback);
    void authWithPassword(final String email, final String pass, final Callback callback);
    void authCustomToken(final Callback callback);


    interface Callback {
        void onSuccess(String uid);
        void onError(final String message);
    }
}
