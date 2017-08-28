package com.quiz.series.tvseriesquiz.interactors.syncronize;

/**
 * Created by Emilio on 30/06/2016.
 */

public interface SyncronizeInterface {

    interface Callback {
        void notifyError(String message);
        void notifySuccess(boolean newDataAvailable);
        void notifyDownload();
    }

    void execute(Callback callback);

}
