package com.fmss.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String cvPath;
    private String tag;

    private AdvertDto advertDto;

    public CandidateDto(String firstName , String lastName , String email , String phone , String tag){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.phone=phone;
        this.tag=tag;
    }
}
