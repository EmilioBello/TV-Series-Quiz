package com.quiz.series.tvseriesquiz.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by Emilio on 11/02/2016.
 */

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ADQuestion extends ADEntity {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correct;
    private int season;
    private int episode;
    private String language;
    private int serieCode;
}
