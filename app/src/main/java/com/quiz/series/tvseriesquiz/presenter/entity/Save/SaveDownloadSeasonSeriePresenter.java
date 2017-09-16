package com.quiz.series.tvseriesquiz.presenter.entity.Save;

import com.quiz.series.tvseriesquiz.interactors.entity.Save.SaveDownloadSeasonSerieInteractor;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;

/**
 * Created by Emilio on 17/08/2017.
 */

public class SaveDownloadSeasonSeriePresenter extends SaveEntityPresenter{

    public SaveDownloadSeasonSeriePresenter(final ADSchema schema, final int code) {
        super(code, schema);
    }

    @Override
    public void initialize(){
        interactor = new SaveDownloadSeasonSerieInteractor(executor, schema, code);
        interactor.execute();
    }
}
