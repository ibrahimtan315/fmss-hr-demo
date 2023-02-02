package com.fmss.hr.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public final class UserRequest {
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String phoneNumber;
    private String fullName;
    private String email;
    private String password;
    private LocalDate lastLoginDate;
    private LocalDate birthday;
    private LocalDate startingDateOfEmployment;
    private String level;
    private BigDecimal salary;
    private String department;
    private String role;
    private Boolean status;
    private String address;
    private String title;
    private String city;
    private String country;
    private String postalCode;
}
