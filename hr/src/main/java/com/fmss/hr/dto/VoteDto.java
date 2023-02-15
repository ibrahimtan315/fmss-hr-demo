package com.fmss.hr.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteDto {

    private Long id;
    private Long surveyId;
    private Long surveyOptionsId;
    private Long userId;
}
