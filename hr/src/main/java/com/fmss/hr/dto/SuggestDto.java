package com.fmss.hr.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class SuggestDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
