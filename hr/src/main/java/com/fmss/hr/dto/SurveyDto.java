package com.fmss.hr.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@Data
public class SurveyDto {
    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private List<SurveyOptionsDto> options;
    private Boolean status;

}
