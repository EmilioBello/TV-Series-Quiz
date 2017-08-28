package com.quiz.series.tvseriesquiz.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by Emilio on 10/10/2016.
 */

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class ADLanguage extends ADEntity {
    private String alias;
    private String value;
    private String language;
}
