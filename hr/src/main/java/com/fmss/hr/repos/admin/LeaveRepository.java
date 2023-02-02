package com.fmss.hr.repos.admin;

import com.fmss.hr.dto.projection.LeaveProjection;
import com.fmss.hr.dto.projection.LeaveUserProjection;
import com.fmss.hr.entities.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {


    @Query(value = "SELECT u.first_name as firstName, u.last_name as lastName, u.identity_number as identityNumber, " +
            "ex.leave_end_c as leaveEndC, ex.leave_end_time as leaveEndTime, ex.leave_start_c as leaveStartC, ex.leave_start_time as leaveStartTime,ex.leave_statement as leaveStatement,ex.leave_type as leaveType,ex.return_date as returnDate,ex.total_leave as totalLeave,ex.status as status, ex.id as id FROM leave ex INNER JOIN users u ON ex.user_id = u.id WHERE ex.status=true"
            , nativeQuery = true)
    Page<LeaveProjection> pagination(Pageable pageable);


    @Query(value ="SELECT ex.leave_end_c as leaveEndC, ex.leave_end_time as leaveEndTime, ex.leave_start_c as leaveStartC, ex.leave_start_time as leaveStartTime,ex.leave_statement as leaveStatement,ex.leave_type as leaveType,ex.return_date as returnDate,ex.total_leave as totalLeave,ex.status as status, ex.id as id FROM leave ex INNER JOIN users u ON ex.user_id = u.id WHERE ex.status=true and ex.user_id= :id"
            , nativeQuery = true)
    Page<LeaveUserProjection> userPageable(Long id, Pageable pageable);

    @Query(value = "SELECT le.id as id , le.leave_end_c as leaveEndC , le.leave_end_time as leaveEndTime , " +
            "le.leave_start_time as leaveStartTime , le.leave_start_c as leaveStartC , " +
            "le.leave_statement as leaveStatement , le.return_date as returnDate ,le.total_leave as totalLeave , " +
            "le.user_id as userId , u.first_name as firstName , u.last_name as lastName , " +
            "le.confirmation as confirmation FROM Leave le INNER JOIN users u ON le.user_id=u.id  where le.manager_id=:managerId  " +
            "AND le.confirmation='PENDING' ", nativeQuery = true)
    List<LeaveProjection> findAllPendingLeavesByManagerId(@Param("managerId") Long managerId);
}
