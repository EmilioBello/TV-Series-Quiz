package com.quiz.series.tvseriesquiz.presenter.entity.Save;

import com.quiz.series.tvseriesquiz.interactors.entity.Save.SaveDownloadSerieInteractor;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSchema;

/**
 * Created by Emilio on 17/08/2017.
 */

public class SaveDownloadSeriePresenter extends SaveEntityPresenter{

    public SaveDownloadSeriePresenter(final ADSchema schema, final int code) {
        super(code, schema);
    }

    @Override
    public void initialize(){
        interactor = new SaveDownloadSerieInteractor(executor, schema, code);
        interactor.execute();
    }
}
