package com.quiz.series.tvseriesquiz.model.datastore.realm;

/**
 * Created by Emilio on 29/06/2016.
 */

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ADRealm {
    private Context context;
    private static final long REALM_SCHEMA_VERSION = 1;
    private static RealmConfiguration config0;

    private static Realm realm;
    //protected RealmConfiguration config1;

    public ADRealm(Context context) {
        this.context = context;
        this.config0 = getConfig();
    }

    public Realm getInstance() {
        return Realm.getInstance(config0);
    }

    public synchronized RealmConfiguration getConfig() {
        if (config0 == null) {
            config0 = new RealmConfiguration.Builder(context).name(Realm.DEFAULT_REALM_NAME).schemaVersion(REALM_SCHEMA_VERSION).migration(new ADMigration()).build();
        }

        return config0;
    }
}
