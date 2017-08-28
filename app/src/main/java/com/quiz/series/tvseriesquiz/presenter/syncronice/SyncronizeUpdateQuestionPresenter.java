package com.quiz.series.tvseriesquiz.presenter.syncronice;

import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.executor.MainThreadImpl;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInteractorQuestion;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInterface;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeUpdateQuestionInteractor;
import com.quiz.series.tvseriesquiz.presenter.PresenterInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Emilio on 23/08/2017.
 */

public class SyncronizeUpdateQuestionPresenter extends PresenterInterface {
    final private SyncronizeQuestionsPresenter.View view;
    private SyncronizeUpdateQuestionInteractor interactor;


    public SyncronizeUpdateQuestionPresenter(final SyncronizeQuestionsPresenter.View view, final int codeSerie, final String language, final long update) {
        this.view = view;

        ExecutorService executor = Executors.newFixedThreadPool(1);
        MainThread mainThread = new MainThreadImpl();
        interactor = new SyncronizeUpdateQuestionInteractor(executor, mainThread, codeSerie, language, update);
    }

    @Override
    public void initialize() {
        interactor.execute(new SyncronizeInterface.Callback() {

            @Override
            public void notifyError(String message) {

            }

            @Override
            public void notifySuccess(boolean newDataAvailable) {
                if(view != null){
                    view.downloadIsDone();
                }
            }

            @Override
            public void notifyDownload() {

            }
        });
    }

    @Override
    public void resume(boolean refreshData) {

    }

    @Override
    public void pause() {
        //Empty
    }

    public interface View {
        void downloadIsDone();
    }
}
