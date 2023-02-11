package com.fmss.hr.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmss.hr.dto.SurveyOptionsDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class SurveyRequest {

    private String title;

    private String description;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private List<SurveyOptionsDto> options;
    private Boolean status;

}