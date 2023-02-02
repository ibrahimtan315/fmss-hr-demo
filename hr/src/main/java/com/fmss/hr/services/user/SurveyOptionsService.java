package com.fmss.hr.services.user;

import com.fmss.hr.dto.SurveyOptionsDto;

import java.util.List;

public interface SurveyOptionsService {

    void saveSurveyOptionsList(List<SurveyOptionsDto> surveyOptionsDtoList);

}
