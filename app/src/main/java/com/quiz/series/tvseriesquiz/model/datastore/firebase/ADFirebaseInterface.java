package com.quiz.series.tvseriesquiz.model.datastore.firebase;

/**
 * Created by Emilio on 29/06/2016.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.realm.RealmObject;


public interface ADFirebaseInterface {
    void downloader(ADFirebase.Callback callback);
    void downloader(int codeSerie, String language, final ADFirebase.Callback callback);
    public void downloaderUpdateQuestion(final int codeSerie, final String language, final long update, final ADFirebase.Callback callback);
    void downloader(final int codeSerie, final String language, final int season, final ADFirebase.Callback callback);
    void syncronice(List<RealmObject> daos);

    interface Callback {
        void onSuccess();
        void onError(final String message);
    }

    interface CallbackVersion {
        void onSuccess(HashSet<String> versions);
        void onError(final String message);
    }

    interface CallbackConfig {
        void onSuccess(Map<String, String> config);
        void onError(final String message);
    }
}
