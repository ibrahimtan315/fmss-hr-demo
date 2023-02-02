package com.fmss.hr.dto.projection;

import java.time.LocalDateTime;

public interface EventUserProjection {

    Long getCandidateId();
    LocalDateTime getDate();
    LocalDateTime getEndDate();
    Boolean getIsOnline();
    String getStart_Url();
    String getFirstName();
    String getLastName();
    String getStatus();


}
