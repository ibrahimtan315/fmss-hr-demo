package com.fmss.hr.services.admin.impl;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.dto.SurveyOptionsDto;
import com.fmss.hr.dto.request.SurveyRequest;
import com.fmss.hr.dto.request.VoteRequest;
import com.fmss.hr.entities.Advert;
import com.fmss.hr.entities.Survey;
import com.fmss.hr.entities.SurveyOptions;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.SurveyMapper;
import com.fmss.hr.mapper.SurveyOptionsMapper;
import com.fmss.hr.repos.admin.SurveyOptionsRepository;
import com.fmss.hr.repos.admin.SurveyRepository;
import com.fmss.hr.repos.user.UserRepository;
import com.fmss.hr.services.admin.SurveyOptionsService;
import com.fmss.hr.services.admin.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final SurveyOptionsService surveyOptionsService;
    private final SurveyOptionsMapper surveyOptionsMapper;
    private final SurveyOptionsRepository surveyOptionsRepository;

    @Override
    public SurveyDto createSurvey(SurveyRequest surveyRequest) {
        Survey survey = surveyMapper.toSurveyFromSurveyCreateRequest(surveyRequest);
        List<SurveyOptionsDto> surveyOptionsDtoList = surveyRequest.getOptions().stream().toList();
        surveyOptionsService.saveSurveyOptionsList(surveyOptionsDtoList);
        return surveyMapper.toSurveyDto(surveyRepository.save(survey));
    }

    @Override
    public void deleteSurveyById(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    @Override
    public SurveyDto updateSurvey(SurveyDto surveyDto, Long id) {
       List<SurveyOptions> surveyOptionsList =
               surveyDto.getOptions().stream().map(surveyOptionsMapper::toSurveyOptions).collect(Collectors.toList());
        Survey survey = surveyRepository.findById(id).orElse(null);
        if(survey != null){
            survey.setTitle(surveyDto.getTitle());
            survey.setDescription(surveyDto.getDescription());
            survey.setStatus(surveyDto.getStatus());
            survey.setStartDate(surveyDto.getStartDate());
            survey.setEndDate(surveyDto.getEndDate());
            survey.setOptions(surveyOptionsList);
            surveyRepository.save(survey);
            return surveyMapper.toSurveyDto(survey);
        }
        return null;
    }

    @Override
    public SurveyDto getSurveyById(Long id) {
        Survey survey = surveyRepository.getSurveyById(id);
        return surveyMapper.toSurveyDto(survey);
    }

    @Override
    public List<SurveyDto> getAllSurvey() {

        List<Survey> surveyList = surveyRepository.findAll();
        return surveyList.stream().map(surveyMapper::toSurveyDto).collect(Collectors.toList());
    }

    @Override
    public List<SurveyDto> getAllSurveyWithStatus(Boolean isActive, int pageNum, String title) {

        Pageable elements = PageRequest.of(pageNum - 1, 6);
        if (isActive && title.length() == 0) {
            List<Survey> allElements = surveyRepository.findAllByStatus(isActive, elements);
            List<SurveyDto> elementDtoList = new ArrayList<>();
            allElements.forEach(p -> elementDtoList.add(surveyMapper.toSurveyDto(p)));
            return elementDtoList;

        } else if (isActive && title.length() > 0) {
            List<SurveyDto> filteredElements = new ArrayList<>();
            int totalSurveys = totalSurveyCountWithStatus(isActive) / 6;
            int limit;

            if (totalSurveys >= 1) {
                limit = totalSurveys;
            } else {
                limit = 6;
            }
            for (int i = 0; i < limit; i++) {

                if (filteredElements.size() == 6) {
                    return filteredElements;
                }
                elements = PageRequest.of(i + pageNum - 1, 6);
                List<Survey> allElements = surveyRepository.findAllByStatusAndTitleContainingIgnoreCase(isActive, title, elements);
                List<SurveyDto> elementDtoList = new ArrayList<>();
                allElements.forEach(p -> elementDtoList.add(surveyMapper.toSurveyDto(p)));
                filteredElements.addAll(elementDtoList);
            }
            return filteredElements;
        }

        return null;
    }
    @Transactional
    public int totalSurveyCountWithStatus(Boolean status) {
        if (!status) {
            return surveyRepository.surveyCount();
        } else {
            return surveyRepository.surveyCountWithStatus(status);
        }
    }

    public Boolean voteOption(VoteRequest voteRequest){
        if(voteRequest!=null){
            Survey survey = surveyRepository.findById(voteRequest.getSurveyId()).orElse(null);
            SurveyOptions surveyOptions = surveyOptionsRepository.findById(voteRequest.getSurveyOptionId()).orElse(null);
                if(surveyOptionsRepository.findUserVote(voteRequest.getUserId(),voteRequest.getSurveyOptionId())!=null){
                    return false;
                }else {
                SurveyOptions surveyOptionsUpdate =
                        new SurveyOptions(voteRequest.getSurveyOptionId(),surveyOptions.getOption(), voteRequest.getUserId(),survey);
                surveyOptionsRepository.save(surveyOptionsUpdate);
            return true;
            }
        } else
            return false;
    }

    public int voteCount(Long surveyId){
        List<Long> surveyOptionsIdList = surveyOptionsRepository.findOptionsOfSurvey(surveyId);


        return 1;
    }

}