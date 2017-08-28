package com.quiz.series.tvseriesquiz.model.entity;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jose on 16/4/15.
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ADEntity implements Serializable {
    protected int code;
    protected boolean active;
    protected Date createdAt;
    protected Date updatedAt;
}