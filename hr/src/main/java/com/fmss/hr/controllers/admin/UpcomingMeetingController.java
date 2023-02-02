package com.fmss.hr.controllers.admin;

import com.fmss.hr.common.ApiResponse;
import com.fmss.hr.common.constant.GenericMessages;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.EventDto;
import com.fmss.hr.dto.projection.ClosestMeetingsProjection;
import com.fmss.hr.dto.response.EventResponseDto;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.services.UpcomingMeeting.UpcomingMeetingService;
import com.fmss.hr.services.UpcomingMeeting.UpcomingMeetingServiceImpl;
import com.fmss.hr.services.admin.OldEventService;
import com.fmss.hr.services.user.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upcomingMeetings")
public class UpcomingMeetingController {
    private UpcomingMeetingService upcomingMeetingService;

    private CandidateService candidateService;
    private OldEventService oldEventService;



    public UpcomingMeetingController(UpcomingMeetingService upcomingMeetingService, CandidateService candidateService,OldEventService oldEventService) {
        this.upcomingMeetingService = upcomingMeetingService;
        this.candidateService= candidateService;
        this.oldEventService=oldEventService;

    }

    @GetMapping("/{pageNum}")
    public ResponseEntity<List<EventResponseDto>> upcomingMeetings(@PathVariable int pageNum){
        List<EventResponseDto> result = new ArrayList<>();
        List<EventDto> upcomingMeetings= upcomingMeetingService.getUpcomingMeetings(pageNum);

        if(upcomingMeetings!=null){
            upcomingMeetings.forEach(i ->result.add(convertToResponse(i)));
            return ResponseEntity.status(HttpStatus.OK).body(result);/*(result, GenericMessages.UPCOMING_MEETINGS_LISTED,HttpStatus.OK.value()))*/
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/closestMeetings")
    public ResponseEntity<List<ClosestMeetingsProjection>> closestMeetings(){
        List<ClosestMeetingsProjection> closestMeetings=oldEventService.getClosestMeetings();
        return ResponseEntity.status(HttpStatus.OK).body(closestMeetings);
    }

    @GetMapping("/upcomingMeetingCount")
    public ResponseEntity<?> getUpcomingMeetingCount(){
        return ResponseEntity.status(HttpStatus.OK).body(upcomingMeetingService.closestMeetingCount());
    }

    private EventResponseDto convertToResponse(EventDto eventDto){

     CandidateDto dto =candidateService.getCandidateById(eventDto.getCandidateId());
     EventResponseDto eventResponseDto= new EventResponseDto();
     eventResponseDto.setCandidateId(eventDto.getCandidateId());
     eventResponseDto.setUserId(eventDto.getUserId());
     eventResponseDto.setDate(eventDto.getDate());
     eventResponseDto.setEndDate(eventDto.getEndDate());
     eventResponseDto.setIsOnline(eventDto.getIsOnline());
     eventResponseDto.setStatus(eventDto.getStatus());
     eventResponseDto.setFirstName(dto.getFirstName());
     eventResponseDto.setLastName(dto.getLastName());
     eventResponseDto.setMeetingResponse(eventDto.getMeetingResponse());
        return eventResponseDto;
    }
}
