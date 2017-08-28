package com.quiz.series.tvseriesquiz.presenter.syncronice;

import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.executor.MainThreadImpl;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInteractorQuestion;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInterface;
import com.quiz.series.tvseriesquiz.presenter.PresenterInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Emilio on 15/10/2016.
 */

public class SyncronizeQuestionsPresenter extends PresenterInterface {

    final private View view;
    private SyncronizeInteractorQuestion interactor;


    public SyncronizeQuestionsPresenter(final View view, final int codeSerie, final String language, final int season) {
        this.view = view;

        ExecutorService executor = Executors.newFixedThreadPool(1);
        MainThread mainThread = new MainThreadImpl();
        interactor = new SyncronizeInteractorQuestion(executor, mainThread, codeSerie, language,season);
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
