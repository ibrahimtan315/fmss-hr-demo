package com.fmss.hr.services.admin;

import com.fmss.hr.dto.EventDto;
import com.fmss.hr.dto.EventUserDto;
import com.fmss.hr.dto.projection.EventUserProjection;
import com.fmss.hr.entities.Event;
import com.fmss.hr.entities.EventUser;
import com.fmss.hr.repos.admin.EventUserRepository;
import com.fmss.hr.services.user.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventUserService {
    private EventUserRepository eventUserRepository;

    private UserService userService;

    public  EventUserService(EventUserRepository eventUserRepository){
        this.eventUserRepository = eventUserRepository;
    }

    public boolean saveEventUser(EventUser eventUser) {
        eventUserRepository.save(eventUser);
        return true;
    }
    public List<EventUser> findEventUsersByUserId(Long id){
        List<EventUser> eventUserList= eventUserRepository.findAllByUserId(id);
        return eventUserList;
    }
    public List<EventUserProjection> getEventUsers(Long id,int index){
        Pageable elements= PageRequest.of(index-1,6);
        List<EventUserProjection> events=eventUserRepository.getEventUsers(id,elements);
        return events;
    }
    @Transactional
    public int totalEventUserCount(Long id) {
        return eventUserRepository.getEventUsersCount(id);
    }
}
