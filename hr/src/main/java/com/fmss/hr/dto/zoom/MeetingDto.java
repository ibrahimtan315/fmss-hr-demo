package com.fmss.hr.dto.zoom;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetingDto {


    private String start_time;
    private int duration;
    @JsonProperty(value = "settings")
    private MeetingSettings meetingSettings;
}

