package com.fmss.hr.services.admin;

import com.fmss.hr.dto.LeaveDto;
import com.fmss.hr.dto.projection.LeaveProjection;
import com.fmss.hr.dto.projection.LeaveUserProjection;
import com.fmss.hr.entities.Leave;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LeaveService {


    List<LeaveDto> getAllLeave();

    LeaveDto createLeave(LeaveDto leaveDto);

    Page<LeaveProjection> pagination(@RequestParam Integer pageSize,
                                     @RequestParam Integer page);

    Page<LeaveUserProjection> userPagination(@PathVariable Long user_id,
                                             @RequestParam Integer pageSize,
                                             @RequestParam Integer page);

    List<LeaveDto> getPendingLeavesByManagerId(Long managerId);
    LeaveDto approveLeave(Long id);
    LeaveDto rejectLeave(Long id);
}
