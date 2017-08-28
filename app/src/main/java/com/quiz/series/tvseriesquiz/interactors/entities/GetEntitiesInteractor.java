package com.quiz.series.tvseriesquiz.interactors.entities;


import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class GetEntitiesInteractor implements Runnable, GetEntitiesInterface {
    private final Executor executor;
    private GetEntitiesInterface.Callback callback;
    private final MainThread mainThread;
    protected final ADSchema schema;


    protected List<ADEntity> entities;

    public GetEntitiesInteractor(Executor executor, MainThread mainThread, final ADSchema schema) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.schema = schema;
    }


    @Override
    public void execute(GetEntitiesInterface.Callback callback) {
        this.callback = callback;

        //call method override "run" from interface runnable
        this.executor.execute(this);
    }


    /**
     * notify result to UI thread
     */
    @Override
    public void run() {
        if (checkParameters()) {
            notifyError();
        } else {
            ADRepository repository = new ADRepository(schema);

            List<ADEntity> entities = repository.fetchQuery(buildQuery(), getOrder());
            nofityEntitiesLoaded(processResult(entities));
        }
    }


    private boolean checkParameters() {
        return  this.executor == null || this.callback == null;
    }

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onConnectionError();
            }
        });
    }

    private void nofityEntitiesLoaded(final List<ADEntity> entities) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(entities);
            }
        });
    }
}
