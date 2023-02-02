package com.fmss.hr.services.admin.impl;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.entities.Survey;
import com.fmss.hr.mapper.SurveyMapper;
import com.fmss.hr.repos.admin.SurveyRepository;
import com.fmss.hr.services.admin.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    @Override
    public SurveyDto createSurvey(SurveyDto surveyDto) {
        return null;
    }

    @Override
    public void deleteSurveyById(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    @Override
    public SurveyDto updateSurvey(SurveyDto surveyDto, Long id) {
        return null;
    }

    @Override
    public SurveyDto getSurveyById(Long id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        return surveyOptional.map(surveyMapper::toSurveyDto).orElse(null);
    }

    @Override
    public List<SurveyDto> getAllSurvey() {
        return null;
    }

    @Override
    public List<SurveyDto> getAllSurveyWithStatus(Boolean isActive, int pageNum) {
        return null;
    }
}
