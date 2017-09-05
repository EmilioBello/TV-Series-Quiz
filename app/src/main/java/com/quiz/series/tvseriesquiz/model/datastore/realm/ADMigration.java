package com.quiz.series.tvseriesquiz.model.datastore.realm;

/**
 * Created by Emilio on 29/06/2016.
 */

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class ADMigration implements RealmMigration {


    @Override
    public void migrate(DynamicRealm dynamicRealm, long oldVersion, long newVersion) {

        RealmSchema schema = dynamicRealm.getSchema();

        if (oldVersion == 1) {

        }
    }
}
