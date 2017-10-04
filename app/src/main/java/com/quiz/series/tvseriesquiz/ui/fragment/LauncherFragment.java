package com.quiz.series.tvseriesquiz.ui.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quiz.series.tvseriesquiz.R;
import com.quiz.series.tvseriesquiz.presenter.syncronice.SyncronizePresenter;
import com.quiz.series.tvseriesquiz.ui.activity.MainActivity;
import com.quiz.series.tvseriesquiz.util.SimpleDialogsUtils;


public class LauncherFragment extends BaseFragment implements SyncronizePresenter.View {

    private ImageView ivLogo;
    private ProgressBar pbLoading;
    private TextView tvMessage;
    private MainActivity main;

    public static LauncherFragment newInstance(MainActivity main) {
        LauncherFragment fragment = new LauncherFragment();
        fragment.setMain(main);

        return fragment;
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    protected void mapUI(View view) {
        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
    }

    @Override
    protected void init() {
        tvMessage.setText("Actualizando");
        pbLoading.setVisibility(View.VISIBLE);

        SyncronizePresenter presenter = new SyncronizePresenter(this);
        presenter.initialize();
    }

    @Override
    protected String getTitle() {
        return "";
    }


    @Override
    public void showConnectionErrorMessage(String message) {
        tvMessage.setText(message);
        pbLoading.setVisibility(View.GONE);

        SimpleDialogsUtils.singleButtonDialog(
                main,
                getResources().getString(R.string.splashscreen_connectionerrortitle),
                getResources().getString(R.string.splashscreen_internetError),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        main.finishAffinity();
                    }
                },
                getResources().getString(R.string.splashscreen_connectionerrordimissButton)
        ).show();
    }

    @Override
    public void showDownloadingMessage() {
        tvMessage.setText(getResources().getString(R.string.splashscreen_internetError));
        pbLoading.setVisibility(View.GONE);

        SimpleDialogsUtils.singleButtonDialog(
                main,
                getResources().getString(R.string.splashscreen_internetError),
                getResources().getString(R.string.splashscreen_connectionerrortitle),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        main.hideLauncher();
                    }
                },
                getResources().getString(R.string.splashscreen_connectionerrordimissButton)
        ).show();

        main.hideLauncher();
    }


    @Override
    public void showSuccessMessage(boolean newDataAvailable) {
        main.hideLauncher();
    }
}
