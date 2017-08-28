package com.quiz.series.tvseriesquiz.presenter.syncronice;

import com.quiz.series.tvseriesquiz.executor.MainThread;
import com.quiz.series.tvseriesquiz.executor.MainThreadImpl;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInteractor;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInteractorStart;
import com.quiz.series.tvseriesquiz.interactors.syncronize.SyncronizeInterface;
import com.quiz.series.tvseriesquiz.presenter.PresenterInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SyncronizePresenter extends PresenterInterface {

    private View view;
    private SyncronizeInteractor interactor;


    public SyncronizePresenter(View view) {
        this.view = view;

        ExecutorService executor = Executors.newFixedThreadPool(1);
        MainThread mainThread = new MainThreadImpl();
        interactor = new SyncronizeInteractorStart(executor, mainThread);
    }

    @Override
    public void initialize() {
        interactor.execute(new SyncronizeInterface.Callback() {

            @Override
            public void notifyError(String message) {
                view.showConnectionErrorMessage(message);
            }

            @Override
            public void notifySuccess(boolean newDataAvailable) {
                view.showSuccessMessage(newDataAvailable);
            }

            @Override
            public void notifyDownload() {
                view.showDownloadingMessage();
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

        void showConnectionErrorMessage(String message);

        void showDownloadingMessage();

        void showSuccessMessage(boolean newDataAvailable);
    }
}
