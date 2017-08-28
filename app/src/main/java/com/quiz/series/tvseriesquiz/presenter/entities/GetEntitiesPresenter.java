package com.quiz.series.tvseriesquiz.presenter.entities;


import com.quiz.series.tvseriesquiz.interactors.entities.GetEntitiesInterface;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.presenter.PresenterInterface;

import java.util.List;

public abstract class GetEntitiesPresenter extends PresenterInterface {

    protected GetEntitiesInterface interactor;
    private View view;
    private List<ADEntity> currentEntities;

    /**
     * Constructor
     */
    public GetEntitiesPresenter(){}

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
        loadEntities();
    }

    @Override
    public void pause() {

    }

    /**
     * call view interface
     */
    protected void loadEntities() {
        if (view.isReady()) {
            view.showLoading();
        }

        interactor.execute(new GetEntitiesInterface.Callback() {
            @Override
            public void onSuccess(List<ADEntity> entities) {
                showEntities(entities);
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

    private void showEntities(List<ADEntity> entities) {
        if (view.isReady()) {
            currentEntities = entities;

            view.render(entities);
            view.updateTitleWithCountOfEntities(entities.size());
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

        void render(List<ADEntity> entities);

        void showConnectionErrorMessage();

        void updateTitleWithCountOfEntities(final int counter);

        boolean isReady();

        boolean isAlreadyLoaded();
    }
}
