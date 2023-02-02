package com.fmss.hr.dto.projection;

import com.fmss.hr.entities.User;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

public interface CandidateProjectionForMeeting {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhone();
    String getcvPath();
    String getTag();
    int getReferenceId();
    String getUserFirstName();
    String getUserLastName();
}
