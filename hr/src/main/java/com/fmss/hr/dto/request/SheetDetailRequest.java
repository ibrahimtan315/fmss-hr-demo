package com.fmss.hr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetDetailRequest {
    private int timeSpent;
    private String content;
    private Long sheetId;
    private String taskTitle;
}
