package com.fmss.hr.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserInfoDto {
    private String firstName;
    private String lastName;
    private String fullName;
    private String title;
    private String email;
    private String phoneNumber;
    private String photoPath;
}
