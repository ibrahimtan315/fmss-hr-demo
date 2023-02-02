package com.fmss.hr.dto;

import com.fmss.hr.entities.Task;
import com.fmss.hr.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetDto {

    private Long id;
    private String month;
    private int day;
    private String content;
    private int year;
    private int timeSpent;
    private Boolean isFilled;
    private Boolean isHoliday;
}
