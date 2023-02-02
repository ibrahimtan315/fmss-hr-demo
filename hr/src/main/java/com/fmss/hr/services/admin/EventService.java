package com.fmss.hr.services.admin;

import com.fmss.hr.dto.EventDto;
import com.fmss.hr.entities.Event;
import com.fmss.hr.entities.EventUser;
import com.fmss.hr.mapper.EventMapper;
import com.fmss.hr.mapper.MeetingMapper;
import com.fmss.hr.repos.admin.EventRepository;
import com.fmss.hr.repos.admin.MeetingRepository;
import com.fmss.hr.services.admin.zoom.ZoomService;
import com.fmss.hr.services.user.UserService;
import com.fmss.hr.utils.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;
    private final ZoomService zoomService;
    private final DateTimeFormatter dateTimeFormatter;
    private final EventMapper eventMapper;
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    private final EventUserService eventUserService;

    private  final UserService userService;

    public List<EventDto> listAllEvents(Pageable elements){

      return eventRepository.findAll(elements)
                .stream()
                .map(eventMapper::eventToEventDto)
                .collect(Collectors.toList());
    }
    public List<EventDto> listEvents(int index){
        Pageable elements = PageRequest.of(index-1, 12);
        List<EventDto> eventList=listAllEvents(elements);
        return eventList;
    }


    public EventDto scheduleEvent(EventDto eventDto,List<Long> userIds)  {

        //eventDto.setDate(dateTimeFormatter.convertDateToZonedDate(eventDto.getDate()));
        //eventDto.setMeetingDto(dateTimeFormatter.convertDateToZonedDate(eventDto.getDate()));
        //eventDto.getMeetingDto().setStart_time(dateTimeFormatter.convertDateToZonedDate(eventDto.getMeetingDto().getStart_time()));

        eventDto.setMeetingResponse(zoomService.scheduleMeeting(eventDto.getMeetingDto()));
        eventDto.setDate(eventDto.getMeetingResponse().getStart_time());
        eventDto.getMeetingResponse().setSettings(eventDto.getMeetingDto().getMeetingSettings());
        Event savedEvent = eventRepository.save(eventMapper.eventDtoToEvent(eventDto));

        /*for(int i=0;i<userIds.size();i++) {
            EventUser eventUser = new EventUser();
            eventUser.setEvent(savedEvent);
            eventUser.setUser(userService.getUserById(userIds.get(i)));
            eventUserService.saveEventUser(eventUser);
        }*/
        userIds.stream().forEach(i->{
            EventUser eventUser = new EventUser();
            eventUser.setEvent(savedEvent);
            eventUser.setUser(userService.getUserById(i));
            eventUserService.saveEventUser(eventUser);
        });
        return eventDto;
    }

    public EventDto getEventById(Long id){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            return eventMapper.eventToEventDto(event.get());
        }
        return null;
    }

    public List<EventDto> getEventsByCandidateId(Long candidateId){
        return eventRepository.findByCandidateId(candidateId)
                .stream()
                .map(eventMapper::eventToEventDto)
                .collect(Collectors.toList());
    }

    public void deleteEventById(Long id){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            zoomService.deleteMeeting(event.get().getMeeting().getMeeting_id());
            eventRepository.deleteById(id);
        }
    }

    public EventDto updateEvent (Long id , EventDto eventDto){
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()){
            Event presentEvent = event.get();
            presentEvent.setEventDescription(eventDto.getEventDescription());
            presentEvent.setEventName(eventDto.getEventName());
            presentEvent.setStatus(eventDto.getStatus());
            presentEvent.setDate(eventDto.getDate());
            presentEvent.setEndDate(eventDto.getEndDate());
            presentEvent.setIsOnline(eventDto.getIsOnline());
            eventRepository.save(presentEvent);
            return eventMapper.eventToEventDto(presentEvent);
        }
        return null;
    }

}
