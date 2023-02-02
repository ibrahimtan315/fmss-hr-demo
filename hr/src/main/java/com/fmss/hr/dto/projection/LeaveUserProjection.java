package com.fmss.hr.dto.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface LeaveUserProjection {


    String getIdentityNumber();
    String getLeaveType();
    Long getTotalLeave();
    LocalDate getLeaveStartTime();
    LocalDateTime getLeaveStartC();
    LocalDateTime getLeaveEndC();
    LocalDate getLeaveEndTime();
    String getLeaveStatement();
    LocalDate getReturnDate();
}
