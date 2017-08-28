package com.quiz.series.tvseriesquiz.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by jose on 30/6/15.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ADVersion extends ADEntity {

    private String name;
    private int version;
}
