package com.fmss.hr.dto;

import com.fmss.hr.entities.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventUserDto {
    private Long id;
    private List<Event> event;


    public EventUserDto() {

    }
}
