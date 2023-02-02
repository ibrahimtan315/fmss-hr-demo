package com.fmss.hr.mapper;

import com.fmss.hr.dto.LeaveDto;
import com.fmss.hr.entities.Leave;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    LeaveDto leaveToLeaveDto(Leave leave);
    Leave leaveDtoToLeave(LeaveDto leaveDto);
}
