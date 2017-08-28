package com.quiz.series.tvseriesquiz.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quiz.series.tvseriesquiz.ui.activity.MainActivity;

/**
 * Base fragment created to be extended by every fragment in this application. This class provides
 * dependency injection configuration, ButterKnife Android library configuration and some methods
 * common to every fragment.
 *
 */
public abstract class BaseFragment extends Fragment {
    private MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle(getTitle());
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        mapUI(view);
        init();
    }

    public void setActivity(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void setTitle(String title){
        if (mainActivity != null){
            mainActivity.setTitle(title);
        }
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract int getFragmentLayout();
    protected abstract void mapUI(View view);
    protected abstract void init();
    protected abstract String getTitle();
}
