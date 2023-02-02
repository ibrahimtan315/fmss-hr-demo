package com.fmss.hr.dto;

import com.fmss.hr.entities.ConfirmationStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@RequiredArgsConstructor
public class LeaveDto {
    private Long id;
    private String leaveType;
    private Long totalLeave;
    private LocalDate leaveStartTime;
    private LocalDateTime leaveStartC;
    private LocalDateTime leaveEndC;
    private LocalDate leaveEndTime;
    private String leaveStatement;
    private LocalDate returnDate;
    private ConfirmationStatus confirmation;
    private Long userId;
    private String firstName;
    private String lastName;

    public LeaveDto(Long id, String leaveType, Long totalLeave, LocalDate leaveStartTime, LocalDateTime leaveStartC,
                    LocalDateTime leaveEndC, LocalDate leaveEndTime, String leaveStatement, LocalDate returnDate,
                    ConfirmationStatus confirmation, Long userId , String firstName , String lastName) {
        this.id = id;
        this.leaveType = leaveType;
        this.totalLeave = totalLeave;
        this.leaveStartTime = leaveStartTime;
        this.leaveStartC = leaveStartC;
        this.leaveEndC = leaveEndC;
        this.leaveEndTime = leaveEndTime;
        this.leaveStatement = leaveStatement;
        this.returnDate = returnDate;
        this.confirmation = confirmation;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
