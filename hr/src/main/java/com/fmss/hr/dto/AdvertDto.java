package com.fmss.hr.dto;

import lombok.Data;

@Data
public class AdvertDto {

    private Long id;
    private String title;
    private String jobPosition;
    private String mannerOfWork;
    private String description;
    private String department;
    private Boolean status;
}
