package com.fmss.hr.services.admin.impl;

import com.fmss.hr.dto.LeaveDto;
import com.fmss.hr.dto.projection.LeaveProjection;
import com.fmss.hr.dto.projection.LeaveUserProjection;
import com.fmss.hr.entities.ConfirmationStatus;
import com.fmss.hr.entities.Department;
import com.fmss.hr.entities.Leave;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.LeaveMapper;
import com.fmss.hr.repos.admin.LeaveRepository;
import com.fmss.hr.repos.user.UserRepository;
import com.fmss.hr.services.admin.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("leaveService")
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;
    private final LeaveMapper leaveMapper;
    private final DepartmentServiceImpl departmentService;

    @Override
    public List<LeaveDto> getAllLeave(){
        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::leaveToLeaveDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveDto createLeave(LeaveDto leaveDto) {
        leaveDto.setConfirmation(ConfirmationStatus.PENDING);
        Leave leave = leaveMapper.leaveDtoToLeave(leaveDto);
        User user = userRepository.findById(leaveDto.getUserId()).get();
        Department department = departmentService.getById(user.getDepartment().getId());
        leave.setUser(user);
        leave.setManager_id(department.getUser().getId());
        leaveRepository.save(leave);
        return leaveDto;

    }

    @Override
    public Page<LeaveProjection> pagination(@RequestParam Integer pageSize,
                                            @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page,pageSize);
        return leaveRepository.pagination(pageable);

    }

    public Page<LeaveUserProjection> userPagination(@PathVariable Long user_id,
                                                    @RequestParam Integer pageSize,
                                                    @RequestParam Integer page){
        Pageable pageable=PageRequest.of(page,pageSize);
        return leaveRepository.userPageable(user_id,pageable);
    }

    @Override
    public List<LeaveDto> getPendingLeavesByManagerId(Long managerId) {
        List<LeaveDto> result = new ArrayList<>();
        List<LeaveProjection> leaveProjections = leaveRepository.findAllPendingLeavesByManagerId(managerId);
        leaveProjections.forEach
                ( a -> result.add(new LeaveDto(a.getId(),a.getLeaveType(),a.getTotalLeave(),
                        a.getLeaveStartTime(),a.getLeaveStartC(),a.getLeaveEndC(),a.getLeaveEndTime(),
                        a.getLeaveStatement(),a.getReturnDate(),a.getConfirmation(),a.getUserId() ,
                        a.getFirstName(),a.getLastName())));
        return result;
    }

    @Override
    public LeaveDto approveLeave(Long id){
        Leave leave = leaveRepository.findById(id).get();
        leave.setStatus(false);
        leave.setConfirmation(ConfirmationStatus.CONFIRMED);
        leaveRepository.save(leave);
        return leaveMapper.leaveToLeaveDto(leave);
    }

    @Override
    public LeaveDto rejectLeave(Long id) {
        Leave leave = leaveRepository.findById(id).get();
        leave.setStatus(false);
        leave.setConfirmation(ConfirmationStatus.REJECTED);
        leaveRepository.save(leave);
        return leaveMapper.leaveToLeaveDto(leave);
    }

}
