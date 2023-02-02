package com.fmss.hr.dto.projection;

import com.fmss.hr.entities.ConfirmationStatus;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OvertimeProjection {

    String getFirstName();
    String getLastName();
    String getOvertimeDescription();
    int getHour();
    LocalDate getDate();
    ConfirmationStatus getConfirmation();
    @Value("#{target.id}")
    Long getUserId();
    Long getId();

}