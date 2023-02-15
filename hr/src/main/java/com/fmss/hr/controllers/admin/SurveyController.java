package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.SurveyDto;
import com.fmss.hr.dto.SurveyOptionsDto;
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
@CrossOrigin(origins = "*")
public class SurveyController {

    private final SurveyServiceImpl surveyService;


    @GetMapping
    public ResponseEntity<List<SurveyDto>> getAllSurvey(){
        return ResponseEntity.ok(surveyService.getAllSurvey());
    }
    @GetMapping("/{isActive}/{pageNum}")
    public ResponseEntity<List<SurveyDto>> getAllSurveyWithStatus(@PathVariable int pageNum, @PathVariable Boolean isActive, @RequestParam(value="title", required = false) String title){
        return ResponseEntity.ok(surveyService.getAllSurveyWithStatus(isActive,pageNum,title));
    }
    @GetMapping("/count/{isActive}")
    public ResponseEntity<Integer> totalSurveyCountWithStatus(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(surveyService.totalSurveyCountWithStatus(isActive));
    }

    @PostMapping("/create")
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

    @PostMapping("/vote")
    public ResponseEntity<String> voteOption( @Valid @RequestBody VoteRequest voteRequest){
       Boolean check =  surveyService.voteOption(voteRequest);
        if(check)
            return ResponseEntity.status(HttpStatus.OK).body("Voted");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Voted");
    }
    @GetMapping("/vote/check/{userId}/{surveyId}")
    public ResponseEntity<Boolean> checkVoted(@PathVariable Long userId, @PathVariable Long surveyId){
        return ResponseEntity.ok(surveyService.voteCheck(userId,surveyId));
    }
    @GetMapping("/surveyOptions/{surveyId}")
    public ResponseEntity<List<SurveyOptionsDto>> getSurveyOptions(@PathVariable Long surveyId){
        return ResponseEntity.ok(surveyService.getSurveyOptions(surveyId));
    }
    @GetMapping("/surveyOptions")
    public ResponseEntity<List<SurveyOptionsDto>> getAllSurveyOptions(){
        return ResponseEntity.ok(surveyService.getAllSurveyOptions());
    }


}
