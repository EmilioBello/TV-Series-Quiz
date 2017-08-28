package com.quiz.series.tvseriesquiz.presenter.entity.Save;

import com.quiz.series.tvseriesquiz.interactors.entity.Save.SaveEntityInteractor;
import com.quiz.series.tvseriesquiz.interactors.entity.Save.SaveProgressInteractor;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;

/**
 * Created by Emilio on 22/01/2017.
 */

public class SaveProgressPresenter extends SaveEntityPresenter{
    private final int seasonProgress;
    private final int episodeProgress;

    public SaveProgressPresenter(final int code, final ADSchema schema, final int seasonProgress, final int episodeProgress) {
        super(code, schema);

        this.seasonProgress = seasonProgress;
        this.episodeProgress = episodeProgress;
    }

    @Override
    public void initialize(){
        interactor = new SaveProgressInteractor(executor, schema, code, seasonProgress, episodeProgress);
        interactor.execute();
    }
}
