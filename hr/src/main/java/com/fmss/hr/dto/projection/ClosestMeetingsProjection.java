package com.fmss.hr.dto.projection;

import org.springframework.data.convert.JodaTimeConverters;

import java.time.LocalDateTime;

public interface ClosestMeetingsProjection {
    Long getCandidateId();
    LocalDateTime getDate();
    String getFirstName();
    String getLastName();


}
