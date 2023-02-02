package com.fmss.hr.services.user.impl;

import com.fmss.hr.dto.OvertimeDto;
import com.fmss.hr.dto.projection.OvertimeProjection;
import com.fmss.hr.entities.ConfirmationStatus;
import com.fmss.hr.entities.Department;
import com.fmss.hr.entities.Overtime;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.OvertimeMapper;
import com.fmss.hr.repos.user.OvertimeRepository;
import com.fmss.hr.services.admin.impl.DepartmentServiceImpl;
import com.fmss.hr.services.user.OvertimeService;
import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OvertimeServiceImpl implements OvertimeService {
    private final OvertimeRepository overtimeRepository;
    private final UserService userService;
    private final OvertimeMapper overtimeMapper;
    private final DepartmentServiceImpl departmentService;


    public List<Overtime> getAllOvertimes(){
        return overtimeRepository.findAll();
    }
    @Override
    public List<OvertimeDto> getAllOvertimeByUserId(Long userId) {
        return overtimeRepository.findAllByUserId(userId)
                .stream()
                .map(overtimeMapper::overtimeToOvertimeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addOvertime(OvertimeDto overtimeDto){
        User user= userService.getUserById(overtimeDto.getUserId());
        Department department = departmentService.getById(user.getDepartment().getId());
        Overtime overtime1=new Overtime();
        overtime1.setDate(overtimeDto.getDate());
        overtime1.setHour(overtimeDto.getHour());
        overtime1.setOvertimeDescription(overtimeDto.getOvertimeDescription());
        overtime1.setUser(user);
        overtime1.setManager_id(department.getUser().getId());
        overtimeRepository.save(overtime1);
    }

    @Override
    public void deleteOvertime(Long id){
        overtimeRepository.deleteById(id);
    }
    @Override
    public List<OvertimeDto> getPendingOvertimesByManagerId(Long managerId){
        List<OvertimeDto> result = new ArrayList<>();
        List<OvertimeProjection> overtimeProjections = overtimeRepository.findAllPendingOvertimesByManagerId(managerId);
        overtimeProjections.forEach
                (a -> result.add(new OvertimeDto(a.getId(),a.getDate(),a.getHour(),a.getOvertimeDescription(),
                        a.getUserId(),a.getConfirmation(), a.getFirstName(), a.getLastName())));
        return result;
    }
    @Override
    public OvertimeDto approveOvertime(Long id){
        Overtime overtime = overtimeRepository.findById(id).get();
        overtime.setStatus(false);
        overtime.setConfirmation(ConfirmationStatus.CONFIRMED);
        overtimeRepository.save(overtime);
        return overtimeMapper.overtimeToOvertimeDto(overtime);
    }

    @Override
    public OvertimeDto rejectOvertime(Long id){
        Overtime overtime = overtimeRepository.findById(id).get();
        overtime.setStatus(false);
        overtime.setConfirmation(ConfirmationStatus.REJECTED);
        overtimeRepository.save(overtime);
        return overtimeMapper.overtimeToOvertimeDto(overtime);
    }


}
