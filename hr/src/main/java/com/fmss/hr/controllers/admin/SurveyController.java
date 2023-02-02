package com.fmss.hr.controllers.admin;

import com.fmss.hr.services.admin.impl.SurveyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyServiceImpl surveyService;




}
