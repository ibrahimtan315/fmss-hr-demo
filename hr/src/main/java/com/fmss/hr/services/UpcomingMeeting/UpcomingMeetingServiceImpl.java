package com.fmss.hr.services.UpcomingMeeting;

import com.fmss.hr.common.constant.ExceptionMessages;
import com.fmss.hr.dto.EventDto;
import com.fmss.hr.exceptions.CustomException;
import com.fmss.hr.mapper.EventMapper;
import com.fmss.hr.repos.admin.OldEventRepository;
import com.fmss.hr.services.admin.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpcomingMeetingServiceImpl implements UpcomingMeetingService {

    private final OldEventRepository oldEventRepository;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public UpcomingMeetingServiceImpl(OldEventRepository oldEventRepository, EventService eventService, EventMapper eventMapper) {
        this.oldEventRepository = oldEventRepository;
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    public List<EventDto> getUpcomingMeetings(int index){
        Pageable elements = PageRequest.of(index-1, 6);
        List<EventDto> upcomingMeetings;
        upcomingMeetings=eventService.listAllEvents(elements);

        upcomingMeetings= upcomingMeetings.stream().filter(p-> !IsMeetingUpcoming(p)).collect(Collectors.toList());
        if(upcomingMeetings!=null) {
            return upcomingMeetings;
        }
        else{
            throw new CustomException(ExceptionMessages.NO_MEETINGS_FOUND, HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public int closestMeetingCount() {

        return oldEventRepository.getClosestEventCount();
    }

    public boolean IsMeetingUpcoming(EventDto eventDto){
        LocalDateTime currentDate = LocalDateTime.now();
        if(eventDto.getDate().isAfter(currentDate)){
            return false;
        }

        else {
            return true;
        }
    }
}
