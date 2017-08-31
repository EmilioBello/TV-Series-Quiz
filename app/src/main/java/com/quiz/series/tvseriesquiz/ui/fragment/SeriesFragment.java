package com.quiz.series.tvseriesquiz.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.quiz.series.tvseriesquiz.ADConstants;
import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.R;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.presenter.entities.GetEntitiesPresenter;
import com.quiz.series.tvseriesquiz.presenter.entities.GetSeriesPresenter;
import com.quiz.series.tvseriesquiz.presenter.syncronice.SyncronizeQuestionsPresenter;
import com.quiz.series.tvseriesquiz.presenter.syncronice.SyncronizeUpdateQuestionPresenter;
import com.quiz.series.tvseriesquiz.ui.activity.SeasonActivity;
import com.quiz.series.tvseriesquiz.ui.adapter.SerieAdapter;
import com.quiz.series.tvseriesquiz.ui.viewmodel.SeriesViewModel;
import com.quiz.series.tvseriesquiz.util.LanguageUtils;
import com.quiz.series.tvseriesquiz.util.LinearLayoutManager;
import com.quiz.series.tvseriesquiz.util.SharedPreferencesUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.quiz.series.tvseriesquiz.MyApp.context;

/**
 * Created by Emilio on 01/10/2016.
 */

public class SeriesFragment extends BaseFragment implements GetEntitiesPresenter.View, SerieAdapter.ClickListener, View.OnClickListener,
        SyncronizeQuestionsPresenter.View,SyncronizeUpdateQuestionPresenter.View {

    private SerieAdapter adapter;
    private RecyclerView rvSerie;

    private static FrameLayout flDetailSerie;

    public static boolean detailOpen = false;
    public static boolean progressChange = true;
    public boolean isDownload = false;

    //Details
    private TextView tvSeason, tvEpisode, tvComplete, tvStatus;
    private ImageView ivImageBackground, ivAvatar;
    private Button btSeason;

    private List<SeriesViewModel> viewModels = new ArrayList<>();
    private SeriesViewModel item;

    private GetEntitiesPresenter presenter;
    private SyncronizeQuestionsPresenter presenterQuestion;
    private SyncronizeUpdateQuestionPresenter presenterUpdateQuestion;

    @Override
    protected void mapUI(View view) {
        rvSerie = (RecyclerView) view.findViewById(R.id.rvSerie);
        flDetailSerie = (FrameLayout) view.findViewById(R.id.flSerie);

        ivImageBackground = (ImageView) view.findViewById(R.id.ivImageBackground);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);

        tvSeason = (TextView) view.findViewById(R.id.tvSeason);
        tvEpisode = (TextView) view.findViewById(R.id.tvEpisode);
        tvComplete = (TextView) view.findViewById(R.id.tvComplete);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);

        btSeason = (Button) view.findViewById(R.id.btSeason);
    }

    @Override
    protected void init() {
        initializeRecyclerView();
        initializePresenter();

        btSeason.setOnClickListener(this);
    }

    public void initializePresenter(){
        presenter = new GetSeriesPresenter();
        presenter.setView(this);
        presenter.initialize();
    }

    private void initializeRecyclerView() {
        //adapter
        adapter = new SerieAdapter(new ArrayList<SeriesViewModel>(), MyApp.getContext(), this);
        rvSerie.setAdapter(adapter);

        //layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyApp.getContext(), LinearLayoutManager.VERTICAL, false);
        rvSerie.setLayoutManager(layoutManager);
        rvSerie.setHasFixedSize(true);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_series;
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void render(List<ADEntity> entities) {
        viewModels.clear();

        for (ADEntity entity : entities) {

            SeriesViewModel viewModelRow = SeriesViewModel.converterEntitytoViewModel(entity);
            if (viewModelRow != null) {
                viewModels.add(viewModelRow);
            }
        }

        refreshAdapter();
    }

    private void refreshAdapter() {
        //Load adapter
        adapter.clear();
        adapter.addAll(viewModels);

        adapter.notifyDataSetChanged();
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



    @Override
    protected String getTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void onItemClicked(SeriesViewModel item) {
        this.item = item;
        resetSerie();
        showSerie();
        checkDowndloadQuestion();
    }

    private void resetSerie() {
        tvSeason.setText(getString(R.string.serie_season));
        tvEpisode.setText(getString(R.string.serie_episode));
        tvComplete.setText(getString(R.string.serie_complete));
        tvStatus.setText(getString(R.string.serie_status));
    }

    private void showSerie() {
        flDetailSerie.setVisibility(View.VISIBLE);
        detailOpen = true;
        setTitle(item.getName());

        //Images
        Picasso.with(context).load(item.getUrlImageBackground()).into(ivImageBackground);
        Picasso.with(context).load(item.getUrlAvatar()).into(ivAvatar);

        //TextView
        tvSeason.append(item.getSeasonProgress() + context.getString(R.string.serie_of) + item.getSeason());
        tvEpisode.append(item.getEpisodeProgress() + context.getString(R.string.serie_of) + item.getTotalEpisodes());

        if(item.isComplete()){
            tvComplete.append(context.getString(R.string.serie_complete_yes));
        }
        else {
            tvComplete.append(context.getString(R.string.serie_complete_no));
        }

        if(item.getStatus() == 0){
            tvStatus.append(context.getString(R.string.serie_status_atDay));
        }
        else {
            tvStatus.append(context.getString(R.string.serie_status_working));
        }
    }

    private void checkDowndloadQuestion() {
        final int seasonToDownload = item.getSeasonProgress() + 1;
        if(item.getSeasonDownload() == 0){
            presenterQuestion = new SyncronizeQuestionsPresenter(this, item.getCode(), LanguageUtils.getLanguage(), 1);
            presenterQuestion.initialize();
        }
        else if(item.getSeasonProgress() >= item.getSeasonDownload()){
            presenterQuestion = new SyncronizeQuestionsPresenter(this, item.getCode(), LanguageUtils.getLanguage(), seasonToDownload);
            presenterQuestion.initialize();
        }
        else{
            final long update = SharedPreferencesUtils.getDate(ADConstants.gameOfThrones,context);
            presenterUpdateQuestion = new SyncronizeUpdateQuestionPresenter(this, item.getCode(), LanguageUtils.getLanguage(), update);
            presenterUpdateQuestion.initialize();
        }
    }

    public static void closeFragment(){
        flDetailSerie.setVisibility(View.GONE);
        detailOpen = false;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(btSeason.getId() == id && isDownload){
            Intent intent = SeasonActivity.getLaunchIntent(MyApp.getContext(), item.getEpisodeProgress(),
                    item.getSeasonProgress(), item.getCode(), item.getListEpisode());
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                progressChange = true;

                final int episodeProgress = data.getIntExtra(SeasonActivity.EXTRA_EPISODE, -1);
                final int seasonProgress = data.getIntExtra(SeasonActivity.EXTRA_SEASON, -1);

                if(episodeProgress != -1){
                    item.setEpisodeProgress(episodeProgress);
                }

                if(seasonProgress != -1){
                    item.setSeasonProgress(seasonProgress);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public void downloadIsDone() {
        isDownload = true;
    }
}
