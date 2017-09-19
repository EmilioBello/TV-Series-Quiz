package com.quiz.series.tvseriesquiz.model.entity;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by Emilio on 11/02/2016.
 */

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ADSerie extends ADEntity {
    private String name;

    private int season;
    private int totalEpisodes;

    private int seasonProgress;
    private int episodeProgress;

    private boolean downloaded;

    private ArrayList<Integer> listEpisode;

    private boolean complete;
    private int status;

    private String urlAvatar;
    private String urlImageBackground;
}
