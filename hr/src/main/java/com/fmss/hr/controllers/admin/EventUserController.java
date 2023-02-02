package com.fmss.hr.controllers.admin;

import com.fmss.hr.services.admin.EventUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventusers")
public class EventUserController {
    private EventUserService eventUserService;

    public EventUserController(EventUserService eventUserService){
        this.eventUserService = eventUserService;
    }
}
