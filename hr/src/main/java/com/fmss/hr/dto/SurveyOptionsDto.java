package com.fmss.hr.dto;

import com.fmss.hr.entities.Survey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Builder
public class SurveyOptionsDto {



    private Long id;
    private String options;
    private int counter;
    private Long surveyId;


}
