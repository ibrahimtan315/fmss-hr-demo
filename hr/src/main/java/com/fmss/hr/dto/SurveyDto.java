package com.fmss.hr.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SurveyDto {
    private Long id;
    private String title;
    private String description;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Boolean status;

}
