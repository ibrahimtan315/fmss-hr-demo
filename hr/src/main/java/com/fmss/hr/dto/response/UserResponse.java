package com.fmss.hr.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String phoneNumber;
    private String email;
    private String fullName;
    private String lastLoginDate;
    private String birthday;
    private String startingDateOfEmployment;
    private String level;
    private BigDecimal salary;
    private String department;
    private String role;
    private String address;
    private String title;
    private String manager;
    private String age;
    private String city;
    private String country;
    private String postalCode;
    private String password;
    private boolean status;
    private String photoPath;
}
