package com.quiz.series.tvseriesquiz.interactors.entity.Get;

/**
 * Created by Emilio on 10/07/2016.
 */

import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import io.realm.RealmObject;
import io.realm.RealmQuery;

public interface GetEntityInterface {
    void execute(Callback callBack);
    RealmQuery<RealmObject> buildQuery();
    ADEntity processResult(ADEntity entity);

    interface Callback {
        void onSuccess(ADEntity entity);
        void onProductNotFound();
        void onConnectionError();
    }
}
