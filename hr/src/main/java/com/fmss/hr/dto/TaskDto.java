package com.fmss.hr.dto;

import com.fmss.hr.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String taskTitle;
    private String taskDescription;
    private String stashLinks;
    private User creator;
    private Boolean isCompleted;
}
