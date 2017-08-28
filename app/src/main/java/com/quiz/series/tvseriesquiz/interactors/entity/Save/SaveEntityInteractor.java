package com.quiz.series.tvseriesquiz.interactors.entity.Save;

import com.quiz.series.tvseriesquiz.model.datastore.realm.repository.ADRepository;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;

import java.util.concurrent.Executor;

/**
 * Created by Emilio on 22/01/2017.
 */

public abstract class SaveEntityInteractor implements SaveEntityInterface, Runnable{
    protected final Executor executor;
    protected final ADSchema schema;
    protected final int code;

    public SaveEntityInteractor(final Executor executor, final ADSchema schema, final int code) {
        this.executor = executor;
        this.schema = schema;
        this.code = code;
    }

    public void execute(){
        executor.execute(this);
    }

    @Override
    public void run() {
        ADRepository repository = new ADRepository(schema);
        ADEntity entity = createEntity();
        repository.updateValues(entity);
    }
}
