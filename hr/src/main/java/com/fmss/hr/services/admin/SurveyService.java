package com.fmss.hr.services.admin;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.entities.Survey;

import java.util.List;

public interface SurveyService {

    Survey createSurvey(SurveyDto surveyDto);

    void deleteSurveyById(Long surveyId);

    Survey updateSurvey(SurveyDto surveyDto, Long id);

    Survey getSurveyById(Long id);

    List<Survey> getAllSurvey();

    List<Survey> getAllSurveyWithStatus(Boolean isActive, int pageNum);



}
