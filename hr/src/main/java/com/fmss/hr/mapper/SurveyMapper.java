package com.fmss.hr.mapper;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.dto.request.SurveyRequest;
import com.fmss.hr.entities.Survey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    Survey toSurvey(SurveyDto surveyDto);

    SurveyDto toSurveyDto(Survey survey);

    Survey toSurveyFromSurveyCreateRequest(SurveyRequest surveyRequest);
}
