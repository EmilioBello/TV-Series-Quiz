package com.quiz.series.tvseriesquiz.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quiz.series.tvseriesquiz.MyApp;
import com.quiz.series.tvseriesquiz.R;
import com.quiz.series.tvseriesquiz.ui.viewmodel.QuestionViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * AllergenAdapter implements a interface ViewModelAdapter<T> for improvement the comunication
 * with activity/fragment.
 */
public class QuestionPageAdapter extends PagerAdapter implements View.OnClickListener{

    private HashMap<Integer, View> views;

    private List<QuestionViewModel> items;
    private LayoutInflater inflater;
    final private Context context;
    private ClickListener listener;

    private Button btAnswer1, btAnswer2, btAnswer3, btAnswer4;
    private QuestionViewModel viewModel;

    private int positionCurrent;

    public QuestionPageAdapter(ClickListener listener) {
        this(new ArrayList<QuestionViewModel>(), listener);
    }

    public QuestionPageAdapter(List<QuestionViewModel> items, ClickListener listener) {
        this.items = items;
        this.listener = listener;
        this.context = MyApp.getContext();
        this.views = new HashMap<Integer, View>();
    }

    public void addAll(List<QuestionViewModel> elements) {
        //this.items.clear();
        this.items.addAll(elements);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public QuestionViewModel get(int position) {
        return items.get(position);
    }

    public QuestionViewModel getActive() {
        return items.get(positionCurrent);
    }

    public void setPosition(final int position){
        positionCurrent = position;
    }

    public void updateView(int position) {
        View view = views.get(position);

        initView(view, position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //inflate
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_season, container, false);

        //init View
        initView(view, position);

        // Add viewpager_item.xml to ViewPager
        container.addView(view);

        //save view
        views.put(position, view);

        return view;
    }

    private void initView(final View view, final int position) {

        //position
        viewModel = get(position);

        final TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        tvQuestion.setText(viewModel.getQuestion());

        btAnswer1 = (Button) view.findViewById(R.id.btAnswer1);
        btAnswer1.setText(viewModel.getAnswer1());
        btAnswer1.setOnClickListener(this);

        btAnswer2 = (Button) view.findViewById(R.id.btAnswer2);
        btAnswer2.setText(viewModel.getAnswer2());
        btAnswer2.setOnClickListener(this);

        btAnswer3 = (Button) view.findViewById(R.id.btAnswer3);
        btAnswer3.setText(viewModel.getAnswer3());
        btAnswer3.setOnClickListener(this);

        btAnswer4 = (Button) view.findViewById(R.id.btAnswer4);
        btAnswer4.setText(viewModel.getAnswer4());
        btAnswer4.setOnClickListener(this);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);

        views.remove(position);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        final QuestionViewModel viewModel = items.get(positionCurrent);
        boolean correct = false;

        if(btAnswer1.getId() == id){
            correct = viewModel.getCorrect() == 1;
            listener.answerQuestion(correct);
        }
        else if(btAnswer2.getId() == id){
            correct = viewModel.getCorrect() == 2;
            listener.answerQuestion(correct);
        }
        else if(btAnswer3.getId() == id){
            correct = viewModel.getCorrect() == 3;
            listener.answerQuestion(correct);
        }
        else if(btAnswer4.getId() == id){
            correct = viewModel.getCorrect() == 4;
            listener.answerQuestion(correct);
        }
    }

    public interface ClickListener {
        void answerQuestion(boolean correct);
    }

    //We need this method because PagerAdapter has a bug and not refresh the adapter when you change its content and show viewpager in a specific position.
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
