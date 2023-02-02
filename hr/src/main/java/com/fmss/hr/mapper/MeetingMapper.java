package com.fmss.hr.mapper;

import com.fmss.hr.dto.zoom.MeetingResponse;
import com.fmss.hr.entities.Meeting;
import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {

    public Meeting meetingResponseToMeeting(MeetingResponse meetingResponse){
        Meeting meeting = new Meeting();
        meeting.setMeeting_id(Long.valueOf(meetingResponse.getId()));
        meeting.setDuration(meetingResponse.getDuration());
        meeting.setHost_email(meetingResponse.getHost_email());
        meeting.setStart_url(meetingResponse.getStart_url());
        meeting.setStart_time(meetingResponse.getStart_time());
        meeting.setTopic(meetingResponse.getTopic());
        meeting.setStatus(meetingResponse.getStatus());
        meeting.setTimezone(meetingResponse.getTimezone());
        return meeting;
    }

    public MeetingResponse meetingToMeetingResponse(Meeting meeting){
        MeetingResponse meetingResponse = new MeetingResponse();
        meetingResponse.setId(meeting.getMeeting_id());
        meetingResponse.setDuration(meeting.getDuration());
        meetingResponse.setHost_email(meeting.getHost_email());
        meetingResponse.setStatus(meeting.getStatus());
        meetingResponse.setStart_time(meeting.getStart_time());
        meetingResponse.setTopic(meeting.getTopic());
        meetingResponse.setStart_url(meeting.getStart_url());
        meetingResponse.setTimezone(meeting.getTimezone());
        meetingResponse.setHost_id(meeting.getHost_id());

        return meetingResponse;

    }


}
