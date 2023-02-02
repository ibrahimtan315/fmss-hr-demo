package com.fmss.hr.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class AnnouncementRequest {

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
