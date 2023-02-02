package com.fmss.hr.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
@RequiredArgsConstructor
public final class DepartmentDto {
    private String name;
    private Long managerId;
}
