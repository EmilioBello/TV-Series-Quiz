package com.quiz.series.tvseriesquiz.interactors.entity.Get;

/**
 * Created by Emilio on 10/07/2016.
 */

import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.concurrent.Executor;


public abstract class GetEntityInteractor implements Runnable, GetEntityInterface {
    private Executor executor;
    private GetEntityInterface.Callback callback;
    private final MainThread mainThread;
    protected final ADSchema schema;

    protected final int code;


    public GetEntityInteractor(Executor executor, MainThread mainThread, ADSchema schema, final int code) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.schema = schema;
        this.code = code;
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

            ADEntity entity = repository.fetch(buildQuery());

            if (entity != null) {
                nofityEntitiesLoaded(processResult(entity));
            } else {
                notifyError();
            }
        }
    }


    @Override
    public void execute(GetEntityInterface.Callback callback) {
        this.callback = callback;

        //call method override "run" from interface runnable
        this.executor.execute(this);
    }


    private boolean checkParameters() {
        return this.executor == null || this.callback == null;
    }

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onConnectionError();
            }
        });
    }

    private void nofityEntitiesLoaded(final ADEntity entity) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(entity);
            }
        });
    }
}
