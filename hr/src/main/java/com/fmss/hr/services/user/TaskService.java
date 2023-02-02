package com.fmss.hr.services.user;

import com.fmss.hr.dto.TaskDto;
import com.fmss.hr.dto.request.TaskCreateRequest;
import com.fmss.hr.entities.Task;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskCreateRequest taskCreateRequest, Long creatorId);
    List<Task> getAllTasks();
    Boolean getTask(String taskTitle, Long userId);
    Boolean dropTask(String taskTitle, Long userId);
    Task getTaskById(Long taskId);
    TaskCreateRequest updateTask(Long taskId, TaskCreateRequest taskCreateRequest);
    boolean checkIfTaskExists(String taskTitle);
    boolean deleteTaskById(Long taskId);
    boolean checkIfTaskAlreadyTaken(Task task, List<Task> tasks);
    Task getTaskByTitle(String taskTitle);
    List<TaskDto> getUserTasks(Long userId);
    Task getTaskByTitleAndUserId(String taskTitle, Long userId);
    Integer getTotalTaskCompletedByUserId(Long userId);
    Integer getUncompletedTasksByUserId(Long userId);
}
