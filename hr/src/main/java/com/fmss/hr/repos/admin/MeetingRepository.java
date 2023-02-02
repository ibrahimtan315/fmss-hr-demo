package com.fmss.hr.repos.admin;


import com.fmss.hr.entities.Event;
import com.fmss.hr.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
