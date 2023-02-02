package com.fmss.hr.mapper;

import com.fmss.hr.dto.TaskDto;
import com.fmss.hr.dto.request.TaskCreateRequest;
import com.fmss.hr.entities.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task taskDtoToTask(TaskDto task);
    TaskDto taskToTaskDto(Task task);
    List<TaskDto> taskListToTaskDtoList(List<Task> taskList);
    List<Task> taskDtoListToTaskList(List<TaskDto> taskList);
    Task taskCreateToTask(TaskCreateRequest taskRequest);
}
