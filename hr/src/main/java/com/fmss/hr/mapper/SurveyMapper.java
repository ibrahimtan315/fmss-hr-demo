package com.fmss.hr.mapper;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.dto.request.SurveyRequest;
import com.fmss.hr.entities.Survey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    Survey toSurveyFromSurveyRequest(SurveyRequest surveyRequest);
    SurveyDto toSurveyDtoFromSurvey(Survey survey);

}
