package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.request.AnnouncementRequest;
import com.fmss.hr.entities.Announcement;
import com.fmss.hr.services.admin.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Announcement>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.getAll());
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResponseEntity<Announcement> getById(@RequestParam(value = "announcementId") Long announcementId){
        Announcement announcement = announcementService.getById(announcementId);

        if(announcement != null)
            return ResponseEntity.status(HttpStatus.OK).body(announcement);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @RequestMapping(value = "/getAllByStatusISTrue", method = RequestMethod.GET)
    public ResponseEntity<List<Announcement>> getAllByStatusIsTrue(){
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.getAllByStatusIsTrue());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Boolean> create(@RequestPart AnnouncementRequest announcementRequest){
        announcementService.create(announcementRequest);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @RequestMapping(value = "/update/{announcementId}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> update(@PathVariable(value = "announcementId") Long announcementId, @RequestPart AnnouncementRequest announcementRequest){
        Announcement announcement = announcementService.getById(announcementId);

        if(announcement == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);

        announcementService.update(announcementId, announcementRequest);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @RequestMapping(value = "/delete/{announcementId}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "announcementId") Long announcementId){
        Announcement announcement = announcementService.getById(announcementId);

        if(announcement != null){
            announcementService.delete(announcementId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }

    @RequestMapping(value = "/makePassive", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> makePassive(@RequestParam("announcementId") Long announcementId){
        if(announcementService.makePassive(announcementId))
            return ResponseEntity.status(HttpStatus.OK).body(true);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
    }
}
