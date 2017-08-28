package com.quiz.series.tvseriesquiz.ui.viewmodel;

import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADQuestion;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Emilio on 06/11/2016.
 */

@Data
@Builder
public class QuestionViewModel {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;

    public static QuestionViewModel converterEntitytoViewModel(ADEntity entity) {
        QuestionViewModel viewModel = null;

        if (entity.getClass() == ADQuestion.class) {
            ADQuestion question = (ADQuestion) entity;

            viewModel = QuestionViewModel.builder().build();

            viewModel.setQuestion(question.getQuestion());
            viewModel.setAnswer1(question.getAnswer1());
            viewModel.setAnswer2(question.getAnswer2());
            viewModel.setAnswer3(question.getAnswer3());
            viewModel.setAnswer4(question.getAnswer4());
            viewModel.setCorrect(question.getCorrect());
        }

        return viewModel;
    }
}
