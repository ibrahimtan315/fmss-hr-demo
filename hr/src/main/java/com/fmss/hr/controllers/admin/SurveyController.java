package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.dto.request.SurveyRequest;
import com.fmss.hr.dto.request.VoteRequest;
import com.fmss.hr.services.admin.impl.SurveyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @GetMapping("/{isActive}/{pageNum}")
    public ResponseEntity<List<SurveyDto>> getAllSurveyWithStatus(@PathVariable int pageNum, @PathVariable Boolean isActive, @RequestParam String title){
        List<SurveyDto> surveyDtoList = surveyService.getAllSurveyWithStatus(isActive,pageNum,title);
        if (surveyDtoList != null)
            return ResponseEntity.status(HttpStatus.OK).body(surveyDtoList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @RequestMapping(value = "create/{userId}", method = RequestMethod.POST)
    public ResponseEntity<SurveyDto> createSurvey(@Valid @RequestBody SurveyRequest surveyRequest ){
        return ResponseEntity.ok(surveyService.createSurvey(surveyRequest));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSurveyById(@PathVariable Long id){
        surveyService.deleteSurveyById(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable Long id){
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyDto> updateSurvey( @PathVariable Long id,@Valid @RequestBody SurveyDto surveyDto){
        return ResponseEntity.ok(surveyService.updateSurvey(surveyDto,id));
    }

    @PostMapping("/Vote")
    public ResponseEntity<String> voteOption( @RequestBody VoteRequest voteRequest){
       Boolean check =  surveyService.voteOption(voteRequest);
        if(check)
            return ResponseEntity.status(HttpStatus.OK).body(" Voted");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Voted");

    }
    @GetMapping("/voteCount/{surveyId}")
    public ResponseEntity<Integer> voteCount(@PathVariable Long surveyId){
        surveyService.voteCount(surveyId);
        return null;
    }


}
