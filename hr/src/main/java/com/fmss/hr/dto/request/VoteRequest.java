package com.fmss.hr.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoteRequest {
    @NotNull
    private Long surveyId;
    @NotNull
    private Long userId;
    @NotNull
    private Long surveyOptionsId;
}
