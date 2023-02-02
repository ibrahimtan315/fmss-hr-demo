package com.fmss.hr.repos.user;

import com.fmss.hr.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTaskTitle(String taskTitle);
    Task findByTaskTitleAndCreatorId(String taskTitle, Long userId);
    @Query(value = "select count(*) from tasks where is_completed = true and creator_id = :userId", nativeQuery = true)
    int getTotalTasksCompleted(Long userId);
    @Query(value = "select count(*) from tasks where is_completed = false and creator_id = :userId", nativeQuery = true)
    int getTotalUncompletedTasks(Long userId);
}
