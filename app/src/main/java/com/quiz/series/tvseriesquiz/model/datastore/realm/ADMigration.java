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

        if (oldVersion == 0 || oldVersion == -1) {
            /*RealmObjectSchema bannerSchema = schema.get(ADBannerSchema.DB_NAME_OFFLINE);
            bannerSchema.removeField("objectId");
            bannerSchema.addPrimaryKey(ADBannerSchema.COLUMN_CODE);

            RealmObjectSchema categorySchema = schema.get(ADCategorySchema.DB_NAME_OFFLINE);
            categorySchema.removeField("objectId");
            categorySchema.addPrimaryKey(ADCategorySchema.COLUMN_CODE);

            RealmObjectSchema companySchema = schema.get(ADCompanySchema.DB_NAME_OFFLINE);
            companySchema.removeField("objectId");
            companySchema.addPrimaryKey(ADCompanySchema.COLUMN_CODE);

            RealmObjectSchema eventSchema = schema.get(ADEventSchema.DB_NAME_OFFLINE);
            eventSchema.removeField("objectId");
            eventSchema.addPrimaryKey(ADEventSchema.COLUMN_CODE);

            RealmObjectSchema groupSchema = schema.get(ADGroupSchema.DB_NAME_OFFLINE);
            groupSchema.removeField("objectId");
            groupSchema.addField("userid", String.class, FieldAttribute.REQUIRED);
            groupSchema.addPrimaryKey(ADGroupSchema.COLUMN_CODE);

            RealmObjectSchema promotionSchema = schema.get(ADPromotionSchema.DB_NAME_OFFLINE);
            promotionSchema.removeField("objectId");
            promotionSchema.addPrimaryKey(ADPromotionSchema.COLUMN_CODE);

            RealmObjectSchema versionSchema = schema.get(ADVersionSchema.DB_NAME_OFFLINE);
            versionSchema.removeField("objectId");
            versionSchema.addPrimaryKey(ADVersionSchema.COLUMN_CODE);*/
        }
    }
}
