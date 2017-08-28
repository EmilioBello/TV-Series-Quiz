package com.quiz.series.tvseriesquiz.catalog;


import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADLanguage;
import com.quiz.series.tvseriesquiz.presenter.entities.GetEntitiesPresenter;
import com.quiz.series.tvseriesquiz.presenter.entities.GetLanguagesPresenter;

import java.util.HashMap;
import java.util.List;


/**
 * AllergenCatalog return a list of allergens (singleton) for use it from whole part
 */
public class LanguageCatalog implements GetEntitiesPresenter.View {

    static private HashMap<String, String> map;
    static private GetEntitiesPresenter presenter;
    private LoadedCatalog loaded;

    private View view;

    /**
     * get data from a presenter (bus)
     */
    public LanguageCatalog(LoadedCatalog loaded) {
        this.loaded = loaded;
    }

    public LanguageCatalog() {
        loaded = null;
    }

    /**
     * get data from a presenter (bus)
     */
    public void loadCatalog() {
        if (map == null || map.size() == 0) {
            presenter = new GetLanguagesPresenter();
            presenter.setView(this);
            presenter.initialize();
        } else {
            loaded.loaded();
        }
    }

    /**
     * get data from a presenter (bus)
     */
     public void refreshCatalog() {
         map.clear();
         presenter = null;

         presenter = new GetLanguagesPresenter();
         presenter.setView(this);
         presenter.initialize();
    }


    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("You can't set a null view");
        }
        this.view = view;
    }


    static public String getValue(String alias) {
        if (map != null) {
            return map.get(alias);
        } else {
            return null;
        }
    }


    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void render(List<ADEntity> entities) {
        map = new HashMap<String, String>();

        if (entities != null) {
            for (ADEntity entity : entities) {
                ADLanguage language = (ADLanguage) entity;

                map.put(language.getAlias(), language.getValue());
            }
        }

        if (view != null) {
            view.finishLoaded();
        }

        if(loaded != null){
            loaded.loaded();
        }
    }

    @Override
    public void showConnectionErrorMessage() {

    }

    @Override
    public void updateTitleWithCountOfEntities(int counter) {

    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean isAlreadyLoaded() {
        return true;
    }

    /**
     * View interface created to abstract the view
     * implementation used in this presenter.
     */
    public interface View {
        void finishLoaded();
    }
}
