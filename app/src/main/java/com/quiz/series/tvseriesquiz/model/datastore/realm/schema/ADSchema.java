package com.quiz.series.tvseriesquiz.model.datastore.realm.schema;

import java.util.List;

public interface ADSchema {
    public String getNameDBOnline();
    public String getNameDBOffline();
    public Class getEntity();
    public Class getEntityDAO();
    public Class getEntityJSON();
    public ADSchemaType getType(String field);
    public String getOrderDefault();
}
