package com.quiz.series.tvseriesquiz.presenter.entity.Get;


import com.quiz.series.tvseriesquiz.interactors.entity.Get.GetEntityInteractor;
import com.quiz.series.tvseriesquiz.interactors.entity.Get.GetEntityInterface;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.presenter.PresenterInterface;

public abstract class GetEntityPresenter extends PresenterInterface {

    protected GetEntityInteractor interactor;

    private View view;
    private ADEntity currentEntity;

    /**
     * Constructor
     */
    public GetEntityPresenter() {}

    /**
     * Optional
     */
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't set a null view");
        }
        this.view = view;
    }

    /**
     * PresenterInterface
     */
    @Override
    public void initialize() {
        checkViewAlreadySetted();
        loadEntity();
    }

    @Override
    public void resume(boolean refreshData) {
        //refresh Data
        if (refreshData) {
            loadEntity();
        }
    }

    @Override
    public void pause() {
        //Empty
    }


    /**
     * call view interface
     */
    private void loadEntity() {
        if (view.isReady()) {
            view.showLoading();
        }

        interactor.execute(new GetEntityInterface.Callback() {
            @Override
            public void onSuccess(ADEntity entity) {
                currentEntity = entity;
                showEntity(entity);
            }

            @Override
            public void onProductNotFound() {

            }

            @Override
            public void onConnectionError() {
                notifyConnectionError();
            }
        });
    }

    private void notifyConnectionError() {
        if (view.isReady() && !view.isAlreadyLoaded()) {
            view.hideLoading();
            view.showConnectionErrorMessage();
        }
    }

    private void showEntity(ADEntity entity) {
        if (view.isReady()) {

            view.render(entity);
            view.hideLoading();
        }
    }

    private void checkViewAlreadySetted() {
        if (view == null) {
            throw new IllegalArgumentException("Remember to set a view for this presenter");
        }
    }

    /**
     * comunicate with ui
     */
    public interface View {

        void hideLoading();

        void showLoading();

        void render(ADEntity entity);

        void showConnectionErrorMessage();

        boolean isReady();

        boolean isAlreadyLoaded();
    }
}
