package com.fmss.hr.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmss.hr.dto.SurveyOptionsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequest {

    private String title;

    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    private List<SurveyOptionsDto> options;

    private Boolean status;

}