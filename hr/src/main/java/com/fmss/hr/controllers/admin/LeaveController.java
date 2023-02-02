package com.fmss.hr.controllers.admin;


import com.fmss.hr.dto.LeaveDto;
import com.fmss.hr.dto.OvertimeDto;
import com.fmss.hr.dto.projection.LeaveProjection;
import com.fmss.hr.dto.projection.LeaveUserProjection;
import com.fmss.hr.entities.Leave;
import com.fmss.hr.repos.admin.LeaveRepository;
import com.fmss.hr.services.admin.impl.LeaveServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveController {
    private final LeaveServiceImpl leaveServiceImpl;

    @RequestMapping(value = "create/{userId}", method = RequestMethod.POST)
    public ResponseEntity<LeaveDto> createLeave(@RequestBody LeaveDto leaveDto,@PathVariable Long userId){
        return ResponseEntity.ok(leaveServiceImpl.createLeave(leaveDto));
    }

//    @GetMapping("/leavePageable")
//    public Page<LeaveProjection> pagination(@RequestParam Integer pageSize,
//                                            @RequestParam Integer page){
//        return leaveServiceImpl.pagination(pageSize,page);
//    }
//
//    @RequestMapping(value = "leaveUserPageable/{userId}", method= RequestMethod.POST)
//    public Page<LeaveUserProjection> userPagination(@PathVariable Long userId,
//                                                                   @RequestParam Integer pageSize,
//                                                                   @RequestParam Integer page){
//        return leaveServiceImpl.userPagination(userId,pageSize,page);
//    }
    //TODO bu kisim kullanilmiyorsa cikarilacak

    @GetMapping(value = "pending-leaves/{managerId}")
    public ResponseEntity<List<LeaveDto>> getPendingLeaves(@PathVariable("managerId") Long managerId){
        return ResponseEntity.ok(leaveServiceImpl.getPendingLeavesByManagerId(managerId));
    }

    @PutMapping(value = "approve-leave/{leaveId}")
    public ResponseEntity<LeaveDto> approveOvertime(@PathVariable("leaveId") Long id){
        return ResponseEntity.ok(leaveServiceImpl.approveLeave(id));
    }

    @PutMapping(value = "reject-overtime/{overtimeId}")
    public ResponseEntity<LeaveDto> rejectOvertime(@PathVariable("overtimeId") Long id){
        return ResponseEntity.ok(leaveServiceImpl.rejectLeave(id));
    }



}
