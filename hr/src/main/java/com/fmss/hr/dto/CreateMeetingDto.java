package com.fmss.hr.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateMeetingDto {

    EventDto eventDto;

    List<Long> userIds;
}
