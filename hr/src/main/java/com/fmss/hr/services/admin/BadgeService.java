package com.fmss.hr.services.admin;

import com.fmss.hr.repos.admin.BadgeRepository;
import org.springframework.stereotype.Service;

@Service
public class BadgeService {
    private BadgeRepository badgeRepository;

    public BadgeService(BadgeRepository badgeRepository){
        this.badgeRepository = badgeRepository;
    }
}
