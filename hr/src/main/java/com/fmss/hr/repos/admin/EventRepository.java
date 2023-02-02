package com.fmss.hr.repos.admin;

import com.fmss.hr.dto.EventDto;
import com.fmss.hr.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCandidateId(Long CandidateId);


}
