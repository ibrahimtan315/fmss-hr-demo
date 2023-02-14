package com.fmss.hr.services.admin.impl;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.dto.SurveyOptionsDto;
import com.fmss.hr.dto.request.SurveyRequest;
import com.fmss.hr.dto.request.VoteRequest;
import com.fmss.hr.entities.Survey;
import com.fmss.hr.entities.SurveyOptions;
import com.fmss.hr.entities.Vote;
import com.fmss.hr.mapper.SurveyMapper;
import com.fmss.hr.mapper.SurveyOptionsMapper;
import com.fmss.hr.repos.admin.SurveyOptionsRepository;
import com.fmss.hr.repos.admin.SurveyRepository;
import com.fmss.hr.repos.admin.VoteRepository;
import com.fmss.hr.services.admin.SurveyOptionsService;
import com.fmss.hr.services.admin.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private final VoteRepository voteRepository;

    @Override
    public SurveyDto createSurvey(SurveyRequest surveyRequest) {
        Survey survey = surveyMapper.toSurveyFromSurveyRequest(surveyRequest);
        SurveyDto surveyDto = surveyMapper.toSurveyDtoFromSurvey(surveyRepository.save(survey));
        Survey surveyWithId = surveyRepository.getSurveyById(surveyDto.getId());
        List<SurveyOptionsDto> surveyOptionsDto = surveyRequest.getOptions();
        List<SurveyOptions> surveyOptionsList = surveyOptionsDto.stream()
                .map(surveyOptionsMapper::toSurveyOptions).toList();
        surveyOptionsList.forEach(p->p.setSurvey(surveyWithId));
        surveyOptionsList.forEach(p->p.setCounter(0));
        surveyOptionsRepository.saveAll(surveyOptionsList);

        return surveyMapper.toSurveyDtoFromSurvey(survey);
    }
    @Override
    public void deleteSurveyById(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    @Override
    public SurveyDto updateSurvey(SurveyDto surveyDto, Long id) {
//       List<SurveyOptions> surveyOptionsList =
//               surveyDto.getOptions().stream().map(surveyOptionsMapper::toSurveyOptions).collect(Collectors.toList());
//        Survey survey = surveyRepository.findById(id).orElse(null);
//        if(survey != null){
//            survey.setTitle(surveyDto.getTitle());
//            survey.setDescription(surveyDto.getDescription());
//            survey.setStatus(surveyDto.getStatus());
//            survey.setStartDate(surveyDto.getStartDate());
//            survey.setEndDate(surveyDto.getEndDate());
//            survey.setOptions((Set<SurveyOptions>) surveyOptionsList);
//            surveyRepository.save(survey);
//            return surveyMapper.toSurveyDtoFromSurvey(survey);
//        }
        return null;
    }

    @Override
    public SurveyDto getSurveyById(Long id) {
        Survey survey = surveyRepository.getSurveyById(id);
        return surveyMapper.toSurveyDtoFromSurvey(survey);
    }

    @Override
    public List<SurveyDto> getAllSurvey() {

        List<Survey> surveyList = surveyRepository.findAll();
        return surveyList.stream().map(surveyMapper::toSurveyDtoFromSurvey).collect(Collectors.toList());
    }

    @Override
    public List<SurveyDto> getAllSurveyWithStatus(Boolean isActive, int pageNum, String title) {

        Pageable elements = PageRequest.of(pageNum - 1, 10);
        if (isActive && title.length() == 0) {
            List<Survey> allElements = surveyRepository.findAllByStatus(isActive, elements);
            List<SurveyDto> elementDtoList = new ArrayList<>();
            allElements.forEach(p -> elementDtoList.add(surveyMapper.toSurveyDtoFromSurvey(p)));
            return elementDtoList;

        } else if (isActive && title.length() > 0) {
            List<SurveyDto> filteredElements = new ArrayList<>();
            int totalSurveys = totalSurveyCountWithStatus(isActive) / 10;
            int limit;

            if (totalSurveys >= 1) {
                limit = totalSurveys;
            } else {
                limit = 9;
            }
            for (int i = 0; i < limit; i++) {

                if (filteredElements.size() == 10) {
                    return filteredElements;
                }
                elements = PageRequest.of(i + pageNum - 1, 10);
                List<Survey> allElements = surveyRepository.findAllByStatusAndTitleContainingIgnoreCase(isActive, title, elements);
                List<SurveyDto> elementDtoList = new ArrayList<>();
                allElements.forEach(p -> elementDtoList.add(surveyMapper.toSurveyDtoFromSurvey(p)));
                filteredElements.addAll(elementDtoList);
            }
            return filteredElements;
        }
        if (!isActive &&title.length() == 0) {
            List<Survey> elementList = surveyRepository.findAllByStatus(isActive,elements);
            List<SurveyDto> elementDtoList = new ArrayList<>();
            elementList.forEach(survey -> {
                elementDtoList.add(surveyMapper.toSurveyDtoFromSurvey(survey));
            });
            return elementDtoList; //TODO true ise true döndürür, false ise hepsini
        } else if (!isActive &&title.length() > 0) {
            List<SurveyDto> filteredElements = new ArrayList<>();

            int totalSurvey = totalSurveyCount()/10;
            int limit;

            if (totalSurvey >= 1) {
                limit = totalSurvey;
            } else {
                limit = 9;
            }

            for (int i = 0; i < limit; i++) {

                if (filteredElements.size() == 10) {
                    return filteredElements;
                }
                elements = PageRequest.of(i+pageNum-1, 10);
                List<Survey> allElements = surveyRepository.findAllByStatusAndTitleContainingIgnoreCase(isActive,title, elements);
                List<SurveyDto> elementDtoList = new ArrayList<>();
                allElements.forEach(survey -> {
                    elementDtoList.add(surveyMapper.toSurveyDtoFromSurvey(survey));
                });

                filteredElements.addAll(elementDtoList);
            }
            return filteredElements;
        }

        return null;
    }

    public int totalSurveyCountWithStatus(Boolean status) {
        if (!status) {
            return surveyRepository.surveyCountWithStatus(status);
        } else {
            return surveyRepository.surveyCountWithStatus(status);
        }
    }
    public int totalSurveyCount(){
        return surveyRepository.surveyCount();
    }
    public List<SurveyOptionsDto> getSurveyOptions(Long surveyId){
       List<SurveyOptions> surveyOptionsList = surveyOptionsRepository.findAllBySurveyId(surveyId);
        return surveyOptionsList.stream().map(surveyOptionsMapper::toSurveyOptionsDto).collect(Collectors.toList());
    }
    public List<SurveyOptionsDto> getAllSurveyOptions (){
        List<SurveyOptions> surveyOptionsList = surveyOptionsRepository.findAll();
        return surveyOptionsList.stream().map(surveyOptionsMapper::toSurveyOptionsDto).collect(Collectors.toList());
    }
    public Boolean voteOption(VoteRequest voteRequest){


            return false;
    }
    public Boolean voteCheck(Long userId){
       Optional<Vote> check = voteRepository.voteCheck(userId);
       if(!check.isEmpty()){
           return true;
        }else
            return false;
    }


}