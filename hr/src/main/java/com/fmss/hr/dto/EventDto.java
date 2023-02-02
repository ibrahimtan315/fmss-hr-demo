package com.fmss.hr.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmss.hr.dto.zoom.MeetingDto;
import com.fmss.hr.dto.zoom.MeetingResponse;
import com.fmss.hr.entities.EventStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventDto {

    private static final long serialVersionUID = 1L;
    //private Long id;
    private Long candidateId;
    private Long userId;
    private String eventName;
    private String eventDescription;

    private LocalDateTime date;

    private LocalDateTime endDate;
    private Boolean isOnline;
    private EventStatus status;
    private MeetingDto meetingDto;
    private MeetingResponse meetingResponse;

}
