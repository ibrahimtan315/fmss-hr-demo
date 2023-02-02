package com.fmss.hr.repos.user;

import com.fmss.hr.entities.TimeSheet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {

    List<TimeSheet> findAllByUserId(Long userId);
    @Query(value = "select count(*) from time_sheets where month = :month and user_id = :userId", nativeQuery = true)
    int daysOfMonthCountByUserId(String month, Long userId);
    @Query(value = "select DISTINCT(month) from time_sheets where year = :year and user_id = :userId", nativeQuery = true)
    List<String> getMonthsByYearAndUserId(int year, Long userId);
    @Query(value = "select DISTINCT(year) from time_sheets where user_id = :userId", nativeQuery = true)
    List<Integer> getYearsByUserId(Long userId);
    @Query(value = "select count(*) from time_sheets where month = :month and year = :year and is_filled = true and user_id = :userId", nativeQuery = true)
    int getFillCountByMonthAndYear(String month, Integer year, Long userId);
    List<TimeSheet> findAllByUserIdAndMonthAndYearOrderById(Long userId, String month, int year);
    @Query(value = "select SUM(time_spent) from time_sheets where month = :month and year = :year and user_id = :userId", nativeQuery = true)
    Integer getTimeSpentByMonthAndYear(String month, Integer year, Long userId);
    @Query(value = "select count(*) from time_sheets where month = :month and year = :year and is_filled = false and user_id = :userId", nativeQuery = true)
    int getRemainingDaysByMonthAndYear(String month, Integer year, Long userId);
    List<TimeSheet> findAllByMonth(String month, Pageable pageable);
    @Query(value = "select DISTINCT(year) from time_sheets", nativeQuery = true)
    List<Integer> getYears();
    @Query(value = "select DISTINCT(month) from time_sheets where year = :year", nativeQuery = true)
    List<String> getMonthsByYear(int year);
    @Query(value = "select count(month) from time_sheets where user_id = :userId and month = :month", nativeQuery = true)
    Integer getLastMonthDayCountByUser(String month, Long userId);
}
