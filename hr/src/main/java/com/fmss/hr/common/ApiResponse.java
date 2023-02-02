package com.fmss.hr.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ApiResponse<T> {

    private T value;
    private String message;
    private int code;

    /*
    200 = Success = OK
    400 = Error = BAD_REQUEST
     */
}
