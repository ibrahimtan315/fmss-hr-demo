package com.fmss.hr.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BirthdayEventDto {
    private String firstName;
    private String lastName;
    private String photoPath;
    private LocalDateTime birthday;
    private int age;

    public BirthdayEventDto(String firstName, String lastName, String photoPath, LocalDateTime birthdayEventDate, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoPath = photoPath;
        this.birthday = birthdayEventDate;
        this.age = age;
    }
}
