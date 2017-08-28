package com.quiz.series.tvseriesquiz.presenter;

/**
 * Abstract presenter to work as base for every presenter created in the application. This
 * presenter
 * declares some methods to attach the fragment/activity lifecycle.
 *
 */
public abstract class PresenterInterface {

    /**
     * Called when the presenter is initialized, this method represents the start of the presenter
     * lifecycle.
     */
    public abstract void initialize();

    /**
     * Called when the presenter is resumed. After the initialization and when the presenter comes
     * from a pause state.
     */
    public abstract void resume(boolean refreshData);

    /**
     * Called when the presenter is paused.
     */
    public abstract void pause();
}