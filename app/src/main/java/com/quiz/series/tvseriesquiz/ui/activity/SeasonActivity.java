package com.quiz.series.tvseriesquiz.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.quiz.series.tvseriesquiz.R;
import com.quiz.series.tvseriesquiz.model.datastore.realm.schema.ADSerieSchema;
import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADQuestion;
import com.quiz.series.tvseriesquiz.presenter.entities.GetQuestionBySerieLanguageAndEpisodePresenter;
import com.quiz.series.tvseriesquiz.presenter.entity.Save.SaveEntityPresenter;
import com.quiz.series.tvseriesquiz.presenter.entity.Save.SaveProgressPresenter;
import com.quiz.series.tvseriesquiz.presenter.syncronice.SyncronizeQuestionsPresenter;
import com.quiz.series.tvseriesquiz.ui.adapter.QuestionPageAdapter;
import com.quiz.series.tvseriesquiz.ui.animation.DepthPageTransformer;
import com.quiz.series.tvseriesquiz.ui.viewmodel.QuestionViewModel;
import com.quiz.series.tvseriesquiz.ui.viewpager.NonSwipeableViewPager;
import com.quiz.series.tvseriesquiz.util.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emilio on 12/11/2016.
 */

public class SeasonActivity extends InterstitialActivity implements QuestionPageAdapter.ClickListener,
        GetQuestionBySerieLanguageAndEpisodePresenter.View{

    public static final String EXTRA_EPISODE = "extra_episode";
    public static final String EXTRA_SEASON = "extra_season";
    private static final String EXTRA_SERIE_CODE = "extra_serie_code";
    private static final String EXTRA_LIST_EPISODES = "extra_list_episodes";

    private int episode = 1, season = 1;
    private int serieCode = 1;
    private String language;
    private GetQuestionBySerieLanguageAndEpisodePresenter presenter;

    //UI
    private Button btFinish;
    private RelativeLayout rlQuestions, rlFinish;

    private NonSwipeableViewPager vpQuestion;
    private QuestionPageAdapter adapter;

    private boolean gameStarted = false;
    private int position = -1; //poner a -1 como valor normal

    private List<QuestionViewModel> questionViewModels;
    private List<Integer> listEpisodes;

    public static Intent getLaunchIntent(final Context context, final int episode, final int season,
                                         final int serieCode, ArrayList<Integer> listEpisode) {
        if (episode <= 0) {
            throwIllegalArgumentException();
        }

        Intent intent = new Intent(context, SeasonActivity.class);
        intent.putExtra(EXTRA_EPISODE, episode);
        intent.putExtra(EXTRA_SEASON, season);
        intent.putExtra(EXTRA_SERIE_CODE, serieCode);
        intent.putIntegerArrayListExtra(EXTRA_LIST_EPISODES, listEpisode);

        return intent;
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "Episode number is not correct");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        //map extra
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            episode = extras.getInt(EXTRA_EPISODE, 1);
            season = extras.getInt(EXTRA_SEASON, 1);
            serieCode = extras.getInt(EXTRA_SERIE_CODE, 1);
            listEpisodes = extras.getIntegerArrayList(EXTRA_LIST_EPISODES);
        }

        mapUI();
        init();
    }

    @Override
    public void mapUI() {
        vpQuestion = (NonSwipeableViewPager) findViewById(R.id.vpQuestions);

        btFinish = (Button) findViewById(R.id.btfinish);

        rlQuestions = (RelativeLayout) findViewById(R.id.rlQuestions);
        rlFinish = (RelativeLayout) findViewById(R.id.rlFinish);

        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    @Override
    public void init(){
        language = LanguageUtils.getLanguage();
        presenter = new GetQuestionBySerieLanguageAndEpisodePresenter(season, episode, serieCode, language);
        presenter.setView(this);
        presenter.initialize();

        if(!gameStarted){
            initializeViewPage();
        }
    }

    @Override
    protected void setListener(){
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                onBackPressed();
            }
        });
    }

    private void initializeViewPage() {
        //adapter
        adapter = new QuestionPageAdapter(this);
        vpQuestion.setAdapter(adapter);

        vpQuestion.setPageTransformer(true, new DepthPageTransformer());
    }

    public void addQuestions(List<QuestionViewModel> viewModels){
        //Load adapter
        adapter.addAll(viewModels);
        adapter.notifyDataSetChanged();
    }

    public void showQuestions() {
        //Starts with -1 to begin in 0
        position++;
        gameStarted = true;

        if(questionViewModels.size() - 1 == position){
            episode++;
            if(listEpisodes.size() >= season){
                if(episode > listEpisodes.get(season - 1)){
                    episode = 1;
                    season++;

                    SyncronizeQuestionsPresenter presenterQuestion = new SyncronizeQuestionsPresenter(null, serieCode, LanguageUtils.getLanguage(), season + 1);
                    presenterQuestion.initialize();
                }
                init();
            }
            else{
                btFinish.setText(R.string.season_win);
            }

            SaveEntityPresenter presenter = new SaveProgressPresenter(serieCode, new ADSerieSchema(), season, episode);
            presenter.initialize();
        }

        vpQuestion.setCurrentItem(position, true);
        adapter.setPosition(position);
    }



    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void render(List<ADEntity> entities) {
        newList();

        List<QuestionViewModel> questions = new ArrayList<>();

        for(final ADEntity entity: entities){
            final ADQuestion question = (ADQuestion) entity;
            final QuestionViewModel viewModel = QuestionViewModel.converterEntitytoViewModel(question);
            questions.add(viewModel);
        }

        questionViewModels.addAll(questions);
        //Collections.shuffle(questionViewModels);
        showProducts(questions);
    }

    private void newList() {
        if (questionViewModels == null) {
            questionViewModels = new ArrayList<>();
        }
    }

    private void showProducts(List<QuestionViewModel> questions) {
        addQuestions(questions);

        if(!gameStarted){
            showQuestions();
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

    @Override
    public void answerQuestion(boolean correct) {
        if(correct){
            showQuestions();
        }
        else{
            rlQuestions.setVisibility(View.GONE);
            rlFinish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_EPISODE,episode);
        returnIntent.putExtra(EXTRA_SEASON,season);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
