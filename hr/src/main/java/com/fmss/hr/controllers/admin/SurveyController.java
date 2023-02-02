package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.services.admin.impl.SurveyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyServiceImpl surveyService;


    @GetMapping
    public ResponseEntity<List<SurveyDto>> getAllSurvey(){
        return ResponseEntity.ok(surveyService.getAllSurvey());
    }
    @GetMapping(value="/{isActive}")
    public ResponseEntity<List<SurveyDto>> getAllSurveyWithStatus(@PathVariable int pageNum, @PathVariable Boolean isActive){
        List<SurveyDto> surveyDtoList = surveyService.getAllSurveyWithStatus(isActive,pageNum);
        return ResponseEntity.ok(surveyService.getAllSurveyWithStatus(isActive,pageNum));
    }
    @PostMapping
    public ResponseEntity<SurveyDto> createSurvey(@Valid @RequestBody SurveyDto surveyDto){
        return ResponseEntity.ok(surveyService.createSurvey(surveyDto));
    }
    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSurveyById(@PathVariable Long id){
        surveyService.deleteSurveyById(id);
    }

    @GetMapping
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable Long id){
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyDto> updateSurvey( @PathVariable Long id,@Valid @RequestBody SurveyDto surveyDto){
        return ResponseEntity.ok(surveyService.updateSurvey(surveyDto,id));
    }



}
