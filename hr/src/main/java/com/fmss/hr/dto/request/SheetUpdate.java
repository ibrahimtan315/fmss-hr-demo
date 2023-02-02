package com.fmss.hr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class SheetUpdate {

    private String month;
    private int day;
    private String content;
    private int year;
    private int timeSpent;
    private String taskTitle;
}
