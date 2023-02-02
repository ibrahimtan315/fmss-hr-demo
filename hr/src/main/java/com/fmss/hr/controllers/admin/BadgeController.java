package com.fmss.hr.controllers.admin;

import com.fmss.hr.services.admin.BadgeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Badge")
public class BadgeController {
    private BadgeService badgeService;

    public BadgeController(BadgeService badgeService){
        this.badgeService = badgeService;
    }
}
