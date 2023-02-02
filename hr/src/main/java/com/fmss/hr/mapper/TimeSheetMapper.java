package com.fmss.hr.mapper;

import com.fmss.hr.dto.TimeSheetDto;
import com.fmss.hr.entities.TimeSheet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeSheetMapper {

    List<TimeSheetDto> ListToDtoList(List<TimeSheet> timeSheetList);
    List<TimeSheet> dtoListToList(List<TimeSheetDto> timeDtoSheetList);
}
