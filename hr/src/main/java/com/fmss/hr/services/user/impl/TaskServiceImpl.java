package com.fmss.hr.services.user.impl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fmss.hr.dto.TaskDto;
import com.fmss.hr.dto.request.TaskCreateRequest;
import com.fmss.hr.entities.Task;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.TaskMapper;
import com.fmss.hr.repos.user.TaskRepository;
import com.fmss.hr.services.user.TaskService;
import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("Task services")
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    @Override
    public TaskDto createTask(TaskCreateRequest taskCreateRequest, Long creatorId) {
        if (getTaskByTitleAndUserId(taskCreateRequest.getTaskTitle(), creatorId) != null)
            return null;

        User user = userService.getUserById(creatorId);

        if (user == null) {
            return null;
        }

        Task task = taskMapper.taskCreateToTask(taskCreateRequest);
        task.setIsCompleted(false);
        task.setCreator(user);
        taskRepository.save(task);
        return taskMapper.taskToTaskDto(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @JsonBackReference
    public Boolean getTask(String taskTitle, Long userId) {
        if (!checkIfTaskExists(taskTitle))
            return false;
        Task task = taskRepository.findByTaskTitle(taskTitle);
        User user = userService.getUserById(userId);
        if (user == null)
            return false;
        if (checkIfTaskAlreadyTaken(task, user.getTasks()))
            return false;
        user.getTasks().add(task);
        return true;
    }

    @Override
    public Boolean dropTask(String taskTitle, Long userId) {
        Task task = getTaskByTitle(taskTitle);
        User user = userService.getUserById(userId);
        if (checkIfTaskAlreadyTaken(task, user.getTasks()))
            return false;
        task.setIsCompleted(true);
        return true;
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public TaskCreateRequest updateTask(Long taskId, TaskCreateRequest taskCreateRequest) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null)
            return null;

        task.setTaskDescription(taskCreateRequest.getTaskDescription());
        task.setTaskTitle(taskCreateRequest.getTaskTitle());
        task.setIsCompleted(taskCreateRequest.getIsCompleted());
        task.setStashLinks(taskCreateRequest.getStashLinks());
        taskRepository.save(task);

        return taskCreateRequest;
    }

    @Override
    public boolean checkIfTaskExists(String taskTitle) {
        return taskRepository.findByTaskTitle(taskTitle) != null;
    }

    @Override
    public boolean deleteTaskById(Long taskId) {
        return false;
    }

    @Override
    public boolean checkIfTaskAlreadyTaken(Task task, List<Task> tasks) {
        return tasks.contains(task);
    }

    @Override
    public Task getTaskByTitle(String taskTitle) {
        return taskRepository.findByTaskTitle(taskTitle);
    }

    @Override
    public List<TaskDto> getUserTasks(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            return null;
        return taskMapper.taskListToTaskDtoList(user.getTasks());
    }

    @Override
    public Task getTaskByTitleAndUserId(String taskTitle, Long userId) {
        return taskRepository.findByTaskTitleAndCreatorId(taskTitle, userId);
    }

    @Override
    public Integer getTotalTaskCompletedByUserId(Long userId) {
        return taskRepository.getTotalTasksCompleted(userId);
    }

    @Override
    public Integer getUncompletedTasksByUserId(Long userId) {
        return taskRepository.getTotalUncompletedTasks(userId);
    }
}