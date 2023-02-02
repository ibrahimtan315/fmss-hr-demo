package com.fmss.hr.controllers.admin;

import com.fmss.hr.common.ApiResponse;
import com.fmss.hr.common.constant.GenericMessages;
import com.fmss.hr.dto.CreateMeetingDto;
import com.fmss.hr.dto.EventDto;
import com.fmss.hr.dto.EventUserDto;
import com.fmss.hr.dto.projection.EventUserProjection;
import com.fmss.hr.entities.Event;
import com.fmss.hr.entities.EventUser;
import com.fmss.hr.query.EventQuery;
import com.fmss.hr.services.admin.EventService;
import com.fmss.hr.services.admin.EventUserService;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventUserService eventUserService;
    private final EventService eventService;
    private final EventQuery eventQuery;

    public EventController(EventService eventService, EventQuery eventQuery,EventUserService eventUserService) {
        this.eventService = eventService;
        this.eventQuery = eventQuery;
        this.eventUserService=eventUserService;
    }

    @GetMapping("/list-all-events/{pageNum}")
    public ResponseEntity<List<EventDto>> listAllEvents(@PathVariable int pageNum) {
        return ResponseEntity.ok(eventService.listEvents(pageNum));
    }

    @PostMapping("/schedule-event")
    public ResponseEntity<EventDto> scheduleEvent(@RequestBody CreateMeetingDto dto) {
        EventDto responseEventDto = eventService.scheduleEvent(dto.getEventDto(),dto.getUserIds());
        return ResponseEntity.ok(responseEventDto);
    }

    @PostMapping("/get-event-by-id/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        EventDto eventDto = eventService.getEventById(id);
        return ResponseEntity.ok(eventDto);
    }

    @PostMapping("/get-candidate-events/{candidateId}")
    public ResponseEntity<List<EventDto>> getEventsByCandidateId(@PathVariable Long candidateId) {
        List<EventDto> list = eventService.getEventsByCandidateId(candidateId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete-event-by-id/{id}")
    public ResponseEntity<Boolean> deleteEventById(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        EventDto targetEventDto = eventService.updateEvent(id, eventDto);
        return ResponseEntity.ok(targetEventDto);
    }

    @PostMapping("/list-events-by-parameters")
    public ResponseEntity<List<EventDto>> listEventsByParameters(@RequestBody EventDto eventDto){
        List<EventDto> eventDtoList = eventQuery.listEventsByParameters(eventDto);
        return ResponseEntity.ok(eventDtoList);
    }

    @GetMapping("/getEventUsersByUserId/{id}/{pageNum}")
    public ResponseEntity<List<EventUserProjection>> listEventUser(@PathVariable("id") Long id,@PathVariable("pageNum") int pageNum){
        List<EventUserProjection> eventUserList= eventUserService.getEventUsers(id,pageNum);
        return ResponseEntity.ok(eventUserList);
    }

    @GetMapping("/getEventUserCount/{id}")
    public ResponseEntity<Integer> eventUserCount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(eventUserService.totalEventUserCount(id));
    }
}
