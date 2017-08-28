package com.quiz.series.tvseriesquiz.presenter.entities;


import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.executor.MainThreadImpl;
import com.quiz.series.tvseriesquiz.interactors.entities.GetSeriesInteractor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GetSeriesPresenter extends GetEntitiesPresenter {

    public GetSeriesPresenter() {
        super();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        MainThread mainThread = new MainThreadImpl();
        interactor = new GetSeriesInteractor(executor, mainThread);
    }

    @Override
    public void resume(boolean refreshData) {

    }
}
