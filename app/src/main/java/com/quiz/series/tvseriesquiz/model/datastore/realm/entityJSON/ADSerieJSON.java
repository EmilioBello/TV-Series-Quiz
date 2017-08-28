package com.quiz.series.tvseriesquiz.model.datastore.realm.entityJSON;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 28/06/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADSerieJSON extends ADEntityJSON {

    public ADSerieJSON() {}

    //ADEntity
    private int code;
    private boolean active;
    private long updatedAt;

    private String name;
    private int seasons;
    private int totalEpisodes;
    private String listEpisode;

    private int seasonProgress;
    private int episodeProgress;

    private boolean complete;
    private int status;

    private String urlAvatar1x;
    private String urlAvatar15x;
    private String urlAvatar2x;
    private String urlAvatar3x;

    private String urlImageBackground1x;
    private String urlImageBackground15x;
    private String urlImageBackground2x;
    private String urlImageBackground3x;
}
