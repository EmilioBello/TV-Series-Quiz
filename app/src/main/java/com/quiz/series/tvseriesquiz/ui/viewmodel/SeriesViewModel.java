package com.quiz.series.tvseriesquiz.ui.viewmodel;

import com.quiz.series.tvseriesquiz.model.entity.ADEntity;
import com.quiz.series.tvseriesquiz.model.entity.ADSerie;

import java.util.ArrayList;

import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Emilio on 07/08/2016.
 */

@Data
@Builder
public class SeriesViewModel {

    private int code;
    private String name;

    private int season;
    private int totalEpisodes;

    private int seasonProgress;
    private int episodeProgress;

    private int seasonDownload;

    private ArrayList<Integer> listEpisode;

    private boolean complete;
    private int status;

    private String urlAvatar;
    private String urlImageBackground;

    public static SeriesViewModel converterEntitytoViewModel(ADEntity entity) {
        SeriesViewModel viewModel = null;

        if (entity.getClass() == ADSerie.class) {
            ADSerie serie = (ADSerie) entity;

            viewModel = SeriesViewModel.builder().build();

            viewModel.setCode(serie.getCode());
            viewModel.setName(serie.getName());

            viewModel.setSeason(serie.getSeason());
            viewModel.setTotalEpisodes(serie.getTotalEpisodes());

            viewModel.setSeasonProgress(serie.getSeasonProgress());
            viewModel.setEpisodeProgress(serie.getEpisodeProgress());

            viewModel.setSeasonDownload(serie.getSeasonDownload());

            viewModel.setListEpisode(serie.getListEpisode());

            viewModel.setComplete(serie.isComplete());
            viewModel.setStatus(serie.getStatus());

            viewModel.setUrlAvatar(serie.getUrlAvatar());
            viewModel.setUrlImageBackground(serie.getUrlImageBackground());
        }

        return viewModel;
    }
}
