package com.fmss.hr.dto.projection;

import com.fmss.hr.entities.ConfirmationStatus;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface LeaveProjection {

    String getFirstName();
    String getLastName();
    String getIdentityNumber();
    String getLeaveType();
    Long getTotalLeave();
    LocalDate getLeaveStartTime();
    LocalDateTime getLeaveStartC();
    LocalDateTime getLeaveEndC();
    LocalDate getLeaveEndTime();
    String getLeaveStatement();
    LocalDate getReturnDate();
    @Value("#{target.id}")
    Long getUserId();
    Long getId();
    ConfirmationStatus getConfirmation();



}
