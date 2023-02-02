package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.EventDto;
import com.fmss.hr.dto.projection.CandidateProjectionForMeeting;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.Comment;
import com.fmss.hr.services.admin.OldEventService;
import com.fmss.hr.services.admin.impl.OldEventServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oldEvents")
public class OldEventController {
    private final OldEventService oldEventService;

    public OldEventController(OldEventService oldEventService) {
        this.oldEventService = oldEventService;
    }

    @GetMapping("/holdingCandidates/{pageNum}")
    public ResponseEntity<List<Candidate>> getHoldingCandidates(@PathVariable int pageNum){
        List<Candidate> holdingCandidates=oldEventService.getOldEventEmployees(pageNum);
        if(holdingCandidates!=null){
            return ResponseEntity.status(HttpStatus.OK).body(holdingCandidates);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
    @GetMapping("/acceptedEmplooyees/{pageNum}")
    public ResponseEntity<List<Candidate>> getAcceptedCandidates(@PathVariable int pageNum){
        List<Candidate> acceptedCandidates=oldEventService.getAcceptedEmployees(pageNum);
        if(acceptedCandidates!=null){
            return ResponseEntity.status(HttpStatus.OK).body(acceptedCandidates);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/rejectedEmplooyees/{pageNum}")
    public ResponseEntity<List<Candidate>> getRejectedCandidates(@PathVariable int pageNum){
        List<Candidate> rejectedCandidates=oldEventService.getRejectedEmployees(pageNum);
        if(rejectedCandidates!=null){
            return ResponseEntity.status(HttpStatus.OK).body(rejectedCandidates);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/holdingCandidates/comments/{id}")
    public ResponseEntity<List<Comment>> getAllCandidateCommentsByCandidate(@PathVariable Long id){
        List<Comment> candidateComments= oldEventService.getCandidateCommentsbyCandidateId(id);
        if(candidateComments!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(candidateComments);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/holdingCandidates/comments-byUserId/{id}")
    public ResponseEntity<List<Comment>> getAllUserCommentsByUser(@PathVariable Long id){
        List<Comment> userComments= oldEventService.getUserCommentsbyUserId(id);
        if (userComments!=null){
            return ResponseEntity.status(HttpStatus.OK).body(userComments);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PutMapping("/setStatusAccepted/{id}")
    public ResponseEntity<?> setStatusAccepted(@PathVariable Long id){
        oldEventService.setEventStatusAccepted(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @PutMapping("/setStatusRejected/{id}")
    public ResponseEntity<?> setStatusRejected(@PathVariable Long id){
        oldEventService.setEventStatusRejected(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @GetMapping("/getCandidatesWithoutMeeting/{pageNum}")
    public ResponseEntity<List<CandidateProjectionForMeeting>> getCandidatesWithoutMeeting(@PathVariable int pageNum){
       List<CandidateProjectionForMeeting> candidatesList= oldEventService.getWaitingCandidates(pageNum);
       return ResponseEntity.status(HttpStatus.OK).body(candidatesList);
    }
    @GetMapping("/getHoldingCandidateCount")
    public ResponseEntity<?> getHoldingCandidateCount(){
        return ResponseEntity.status(HttpStatus.OK).body(oldEventService.totalHoldingCandidateCount());
    }
    @GetMapping("/getAcceptedCandidateCount")
    public ResponseEntity<Integer> getAcceptedCandidateCount(){
        return ResponseEntity.status(HttpStatus.OK).body(oldEventService.totalAcceptedCandidateCount());
    }
    @GetMapping("/getRejectedCandidateCount")
    public ResponseEntity<Integer> getRejectedCandidateCount(){
        return ResponseEntity.status(HttpStatus.OK).body(oldEventService.totalRejectedCandidateCount());
    }
    @GetMapping("/getWaitingCandidateCount")
    public ResponseEntity<Integer> getWaitingCandidateCount(){
        return ResponseEntity.status(HttpStatus.OK).body(oldEventService.totalWaitingCandidateCount());
    }
}
