package com.fmss.hr.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class VoteRequest {

    private Long surveyId;
    private Long userId;
    private Long surveyOptionId;
}
