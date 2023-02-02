package com.fmss.hr.services.UpcomingMeeting;

import com.fmss.hr.dto.EventDto;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public interface UpcomingMeetingService {
    List<EventDto> getUpcomingMeetings(int index);
    int closestMeetingCount();




}
