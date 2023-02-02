package com.fmss.hr.common;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GenericResponse {

    private String message;
    private LocalDate date;

    public GenericResponse(String message){
        this.message = message;
        date = LocalDate.now();
    }

}
