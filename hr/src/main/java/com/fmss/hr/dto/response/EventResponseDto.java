package com.fmss.hr.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmss.hr.dto.zoom.MeetingDto;
import com.fmss.hr.dto.zoom.MeetingResponse;
import com.fmss.hr.entities.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventResponseDto {

    private static final long serialVersionUID = 1L;
    //private Long id;
    private Long candidateId;

    private Long userId;

    private LocalDateTime date;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Boolean isOnline;

    private EventStatus status;


    private String lastName;

    private  String firstName;

    private MeetingResponse meetingResponse;



}
