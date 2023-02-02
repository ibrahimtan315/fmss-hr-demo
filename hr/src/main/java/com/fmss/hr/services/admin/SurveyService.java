package com.fmss.hr.services.admin;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.entities.Survey;

import java.util.List;

public interface SurveyService {

    SurveyDto createSurvey(SurveyDto surveyDto);

    void deleteSurveyById(Long surveyId);

    SurveyDto updateSurvey(SurveyDto surveyDto, Long id);

    SurveyDto getSurveyById(Long id);

    List<SurveyDto> getAllSurvey();

    List<SurveyDto> getAllSurveyWithStatus(Boolean isActive, int pageNum);



}
