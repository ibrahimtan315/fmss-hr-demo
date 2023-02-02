package com.fmss.hr.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String message;

    @Getter
    private final HttpStatus httpStatus;

    public String getMessage(){return message;}
}
