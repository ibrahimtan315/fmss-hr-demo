package com.fmss.hr.mapper;

import com.fmss.hr.dto.SurveyOptionsDto;
import com.fmss.hr.entities.SurveyOptions;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring")
public interface SurveyOptionsMapper {

    SurveyOptions toSurveyOptions(SurveyOptionsDto surveyOptionsDto);

    SurveyOptionsDto toSurveyOptionsDto(SurveyOptions surveyOptions);
}
