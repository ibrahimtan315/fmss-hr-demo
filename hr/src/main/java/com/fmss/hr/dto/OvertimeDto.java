package com.fmss.hr.dto;

import com.fmss.hr.entities.ConfirmationStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class OvertimeDto {

    private Long id;
    private LocalDate date;
    private int hour;
    private String overtimeDescription;
    private Long userId;
    private ConfirmationStatus confirmation;
    private String firstName;
    private String lastName;

    public OvertimeDto(Long id, LocalDate date, int hour, String overtimeDescription, Long userId,
                       ConfirmationStatus confirmation, String firstName, String lastName) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.overtimeDescription = overtimeDescription;
        this.userId = userId;
        this.confirmation = confirmation;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public OvertimeDto() {
    }
}
