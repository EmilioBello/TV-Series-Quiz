package com.quiz.series.tvseriesquiz.presenter.entity.Save;

import com.quiz.series.tvseriesquiz.interactors.entity.Save.SaveEntityInteractor;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Emilio on 22/01/2017.
 */

public abstract class SaveEntityPresenter {

    protected SaveEntityInteractor interactor;
    protected final int  code;
    protected final ADSchema schema;
    protected ExecutorService executor = Executors.newSingleThreadExecutor();

    public SaveEntityPresenter(final int  code, final ADSchema schema){
        this.code = code;
        this.schema = schema;

        executor = Executors.newSingleThreadExecutor();
    }

    public abstract void initialize();
}
