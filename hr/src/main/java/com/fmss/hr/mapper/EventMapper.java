package com.fmss.hr.mapper;

import com.fmss.hr.dto.EventDto;
import com.fmss.hr.entities.Event;
import com.fmss.hr.repos.admin.EventUserRepository;
import com.fmss.hr.utils.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    private final DateTimeFormatter dateTimeFormatter;
    private final MeetingMapper meetingMapper;

    private final EventUserRepository eventUserRepository;

    public EventMapper(DateTimeFormatter dateTimeFormatter, MeetingMapper meetingMapper, EventUserRepository eventUserRepository) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.meetingMapper = meetingMapper;
        this.eventUserRepository = eventUserRepository;
    }
    public EventDto eventToEventDto(Event event){
        EventDto eventDto = new EventDto();
        eventDto.setCandidateId(event.getCandidateId());
        eventDto.setEventName(event.getEventName());
        eventDto.setDate(event.getDate());
        eventDto.setEventDescription(event.getEventDescription());
        eventDto.setIsOnline(event.getIsOnline());
        eventDto.setStatus(event.getStatus());
        eventDto.setEndDate(event.getEndDate());
        eventDto.setMeetingResponse(meetingMapper.meetingToMeetingResponse(event.getMeeting()));
        eventDto.getMeetingResponse().setDuration((event.getMeeting().getDuration()));
        eventDto.getMeetingResponse().setId(event.getMeeting().getMeeting_id());

        return eventDto;
    }

    public Event eventDtoToEvent(EventDto eventDto){
        Event event = new Event();
        event.setCandidateId(eventDto.getCandidateId());
        event.setEventName(eventDto.getEventName());
        event.setEventDescription(eventDto.getEventDescription());
        event.setStatus(eventDto.getStatus());
        event.setIsOnline(eventDto.getIsOnline());
        event.setDate(eventDto.getMeetingResponse().getStart_time());
        event.setEndDate(eventDto.getEndDate());
        event.setMeeting(meetingMapper.meetingResponseToMeeting(eventDto.getMeetingResponse()));
        event.getMeeting().setDuration(eventDto.getMeetingResponse().getDuration());
        event.getMeeting().setTimezone(eventDto.getMeetingResponse().getTimezone());

        return event;
    }
    public List<EventDto> eventListToEventDtoList(List<Event> events){
        List<EventDto> result = new ArrayList<>();
        events.forEach(i -> result.add(eventToEventDto(i)));
        return result;
    }
}
