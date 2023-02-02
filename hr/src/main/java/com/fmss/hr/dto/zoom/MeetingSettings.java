package com.fmss.hr.dto.zoom;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MeetingSettings {

    @JsonProperty(value = "meeting_invitees")
    private List<MeetingEmail> meetingEmail;

}
