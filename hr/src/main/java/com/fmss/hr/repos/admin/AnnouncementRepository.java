package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Transactional
    @Modifying
    @Query(value = "update announcements set status = false where id = :announcementId", nativeQuery = true)
    void makePassive(Long announcementId);
}
