package com.quiz.series.tvseriesquiz.model.datastore.realm.entityDAO;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emilio on 15/02/2016.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADSerieDAO extends RealmObject{

    //ADEntityDAO
    @PrimaryKey
    private int code;

    private Date createdAt;
    private Date updatedAt;
    private boolean active;

    private String name;
    private int seasons;
    private int totalEpisodes;
    private String listEpisode;

    private int seasonProgress;
    private int episodeProgress;

    private boolean downloaded;

    private boolean complete;
    private int status;

    private String urlAvatar;
    private String urlImageBackground;
}
