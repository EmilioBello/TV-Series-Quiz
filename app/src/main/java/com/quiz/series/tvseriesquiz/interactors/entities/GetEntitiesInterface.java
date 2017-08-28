package com.quiz.series.tvseriesquiz.interactors.entities;

import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmQuery;

public interface GetEntitiesInterface {
    void execute(Callback callback);
    String getOrder();
    RealmQuery<RealmObject> buildQuery();
    List<ADEntity> processResult(List<ADEntity> entities);

    interface Callback {
        void onSuccess(final List<ADEntity> entities);
        void onConnectionError();
    }
}
