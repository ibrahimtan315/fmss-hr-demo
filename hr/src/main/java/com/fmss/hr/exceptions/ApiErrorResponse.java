package com.fmss.hr.exceptions;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ApiErrorResponse {
    private int statusCode;

    private String message;

    private LocalDate date;

    public ApiErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        date = LocalDate.now();
    }
}
