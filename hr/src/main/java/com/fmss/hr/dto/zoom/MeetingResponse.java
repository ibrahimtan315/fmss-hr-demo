package com.fmss.hr.dto.zoom;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingResponse {

    private static final long serialVersionUID = 1L;

    private String host_id;
    private String host_email;
    private String topic;
    private String status;
    private LocalDateTime start_time;
    private int duration;
    private String timezone;
    private String start_url;
    private Long id;
    private MeetingSettings settings;

}
