package com.fmss.hr.services.admin;

import com.fmss.hr.dto.request.AnnouncementRequest;
import com.fmss.hr.entities.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAll();
    List<Announcement> getAllByStatusIsTrue();
    Announcement getById(Long id);
    boolean update(Long id, AnnouncementRequest announcement);
    void delete(Long id);
    void create(AnnouncementRequest announcementRequest);
    boolean makePassive(Long id);
}
