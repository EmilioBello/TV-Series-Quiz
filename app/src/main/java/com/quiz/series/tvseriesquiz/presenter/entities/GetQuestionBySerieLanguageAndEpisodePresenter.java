package com.quiz.series.tvseriesquiz.presenter.entities;

import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.executor.MainThreadImpl;
import com.quiz.series.tvseriesquiz.interactors.entities.GetQuestionBySerieLanguageAndEpisodeInteractor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GetQuestionBySerieLanguageAndEpisodePresenter extends GetEntitiesPresenter {

    public GetQuestionBySerieLanguageAndEpisodePresenter(int season, int episode, int serieCode, String language) {
        super();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        MainThread mainThread = new MainThreadImpl();
        interactor = new GetQuestionBySerieLanguageAndEpisodeInteractor(executor, mainThread, season, episode, serieCode, language);
    }

    @Override
    public void resume(boolean refreshData) {

    }
}
