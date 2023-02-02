package com.fmss.hr.mapper;

import com.fmss.hr.dto.OvertimeDto;
import com.fmss.hr.entities.Overtime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OvertimeMapper {

    OvertimeDto overtimeToOvertimeDto(Overtime overtime);

    Overtime overtimeDtoToOvertime(OvertimeDto overtimeDto);

}
