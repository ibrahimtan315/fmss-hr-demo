package com.fmss.hr.services.user.impl;

import com.fmss.hr.dto.SurveyOptionsDto;
import com.fmss.hr.entities.SurveyOptions;
import com.fmss.hr.mapper.SurveyOptionsMapper;
import com.fmss.hr.repos.admin.SurveyOptionsRepository;
import com.fmss.hr.services.admin.SurveyOptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyOptionsServiceImpl implements SurveyOptionsService {

    private final SurveyOptionsMapper surveyOptionsMapper;

    private final SurveyOptionsRepository surveyOptionsRepository;

    @Override
    public void saveSurveyOptionsList(List<SurveyOptionsDto> surveyOptionsDtoList) {
        List<SurveyOptions> surveyOptionsList = surveyOptionsDtoList.stream().map(surveyOptionsMapper::toSurveyOptions)
                .toList();
        surveyOptionsList.stream().forEach(surveyOptionsRepository::save);
    }
}