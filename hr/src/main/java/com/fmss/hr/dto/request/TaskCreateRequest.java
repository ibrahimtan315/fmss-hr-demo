package com.fmss.hr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class TaskCreateRequest {
    private Long id;
    private String taskTitle;
    private String taskDescription;
    private String stashLinks;
    private Boolean isCompleted;
}
