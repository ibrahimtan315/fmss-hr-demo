package com.fmss.hr.dto.request;

import com.fmss.hr.dto.SurveyOptionsDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class SurveyRequest {

    private String title;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<SurveyOptionsDto> options;
    private Boolean status;

}