package com.fmss.hr.services.admin.impl;


import com.fmss.hr.dto.request.AnnouncementRequest;
import com.fmss.hr.entities.Announcement;
import com.fmss.hr.repos.admin.AnnouncementRepository;
import com.fmss.hr.services.admin.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("Announcement Services")
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    @Override
    public List<Announcement> getAllByStatusIsTrue() {
        List<Announcement> announcementList = announcementRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        List<Announcement> result = new ArrayList<>();
        for(Announcement announcement: announcementList){
            if (currentDate.compareTo(announcement.getStartDate()) >= 0 && currentDate.compareTo(announcement.getEndDate()) <= 0) {
                result.add(announcement);
            }
        }
        return result;
    }

    @Override
    public Announcement getById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(Long id, AnnouncementRequest announcement) {
        Announcement announcementObj = announcementRepository.findById(id).orElse(null);

        if(announcementObj != null){
            announcementObj.setTitle(announcement.getTitle());
            announcementObj.setDescription(announcement.getDescription());
            announcementObj.setStartDate(announcement.getStartDate());
            announcementObj.setEndDate(announcement.getEndDate());
            announcementRepository.save(announcementObj);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public void create(AnnouncementRequest announcementRequest) {
        Announcement announcement = new Announcement();

        announcement.setTitle(announcementRequest.getTitle());
        announcement.setDescription(announcementRequest.getDescription());
        announcement.setStartDate(announcementRequest.getStartDate());
        announcement.setEndDate(announcementRequest.getEndDate());
        announcement.setStatus(true);

        announcementRepository.save(announcement);
    }

    @Override
    public boolean makePassive(Long id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);

        if(announcement == null)
            return false;

        announcementRepository.makePassive(id);
        return true;
    }
}
