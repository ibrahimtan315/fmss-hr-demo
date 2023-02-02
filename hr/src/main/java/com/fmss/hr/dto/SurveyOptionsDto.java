package com.fmss.hr.dto;

import com.fmss.hr.entities.Survey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class SurveyOptionsDto {

    private Long id;
    private String option;
    private Long userId;


}
