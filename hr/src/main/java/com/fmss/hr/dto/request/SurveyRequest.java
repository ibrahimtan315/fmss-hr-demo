package com.fmss.hr.dto.request;

import com.fmss.hr.dto.SurveyOptionsDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class SurveyRequest {

    private Long id;

    @NotBlank(message = "Title can not be null!")
    private String title;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotBlank(message = "Survey options can not be null!")
    private List<SurveyOptionsDto> options;

    private Boolean status;

}
