package com.quiz.series.tvseriesquiz.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.quiz.series.tvseriesquiz.R;
import com.quiz.series.tvseriesquiz.ui.fragment.BaseFragment;
import com.quiz.series.tvseriesquiz.ui.fragment.LauncherFragment;
import com.quiz.series.tvseriesquiz.ui.fragment.SeriesFragment;

/**
 * Created by Emilio on 07/10/2015.
 */
public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment = null;
    private int posCurrentFragment = 1;

    public static final String EXTRA_PARAM_VIEW = "extra_view";
    private static final String EXTRA_FRAGMENT = "extra_fragment";

    /**
     * Order element for Drawer Menu
     */
    public static final int FRAGMENT_SERIES     = 1;

    public static Intent getLaunchIntent(final Context context, final int fragment) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent.putExtra(EXTRA_FRAGMENT, fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //map extra
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int fragment = extras.getInt(EXTRA_FRAGMENT, 0);

            if (fragment > 0) {
                showFragment(fragment);
            } else {
                loadLauncherScreen(LauncherFragment.newInstance(this));
            }

        } else {
            if (savedInstanceState == null) {
                loadLauncherScreen(LauncherFragment.newInstance(this));
            }
        }
    }

    private void loadLauncherScreen(LauncherFragment syncr) {
        //add fragment launcher
        FrameLayout relativeLayout = (FrameLayout) findViewById(R.id.flLauncher);
        relativeLayout.setVisibility(View.VISIBLE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flLauncher, syncr)
                .commitAllowingStateLoss();
    }

    private void showFragment(int position) {
        Fragment frag = null;
        BaseFragment baseFragment = null;

        switch (position) {
            case 0:
            case FRAGMENT_SERIES: {
                baseFragment = new SeriesFragment();
                posCurrentFragment = position;
                break;
            }
        }

        if (baseFragment != null) {
            baseFragment.setActivity(this);
            frag = baseFragment;
            loadFragment(frag);
        }
    }

    private void loadFragment(Fragment fragment) {
        currentFragment = fragment;

        // update the activity_game content by replacing fragments
        if (currentFragment != null) {
            /*FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, currentFragment).commit();*/

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (!isFinishing()) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.flContent, currentFragment).commitAllowingStateLoss();
            }

            //Refresh menu
            //invalidateOptionsMenu();
        } else {
            Log.e("Error", "loadFragment: " + posCurrentFragment);
        }
    }

    public void hideLauncher() {
        //remove launcher
        FrameLayout flLauncher = (FrameLayout) findViewById(R.id.flLauncher);
        flLauncher.setVisibility(View.GONE);
        getSupportFragmentManager().popBackStack();

        //load fragment
        int loadFragment = getIntent().getIntExtra(EXTRA_PARAM_VIEW, posCurrentFragment);
        showFragment(loadFragment);
    }

    @Override
    public void onBackPressed(){
        if(FRAGMENT_SERIES == posCurrentFragment) {
            if (SeriesFragment.detailOpen) {
                if (SeriesFragment.progressChange) {
                    SeriesFragment seriesFragment = (SeriesFragment) currentFragment;
                    seriesFragment.initializePresenter();
                }
                SeriesFragment.closeFragment();
                setTitle(R.string.app_name);
            } else {
                super.onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }
    }
}
