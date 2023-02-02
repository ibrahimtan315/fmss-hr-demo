package com.fmss.hr.services.admin.impl;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.entities.Survey;
import com.fmss.hr.repos.admin.SurveyRepository;
import com.fmss.hr.services.admin.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Override
    public Survey createSurvey(SurveyDto surveyDto) {
        return null;
    }

    @Override
    public void deleteSurveyById(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    @Override
    public Survey updateSurvey(SurveyDto surveyDto, Long id) {
        return null;
    }

    @Override
    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Survey> getAllSurvey() {
        return null;
    }

    @Override
    public List<Survey> getAllSurveyWithStatus(Boolean isActive, int pageNum) {
        return null;
    }
}
