package com.fmss.hr.dto;

import com.fmss.hr.entities.Survey;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@Data
public class SurveyOptionsDto {

    private Long id;
    private String option;
    private Long userId;


}
