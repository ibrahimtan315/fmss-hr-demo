package com.fmss.hr.repos.user;

import com.fmss.hr.dto.projection.OvertimeProjection;
import com.fmss.hr.entities.Overtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OvertimeRepository extends JpaRepository<Overtime,Long> {
    List<Overtime> findAllByUserId(Long userId);

    @Query(value = "SELECT o.id as id, o.user_id as userId , u.first_name as firstName , u.last_name as lastName ," +
            " o.overtime_description as overtimeDescription , o.date as date , o.hour as hour , " +
            "o.confirmation as confirmation FROM Overtime o  INNER JOIN users u ON o.user_id=u.id  " +
            "where o.manager_id=:managerId  AND o.confirmation='PENDING'", nativeQuery = true)
    List<OvertimeProjection> findAllPendingOvertimesByManagerId(@Param("managerId") Long managerId);
}
