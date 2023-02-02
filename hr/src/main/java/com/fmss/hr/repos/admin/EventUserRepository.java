package com.fmss.hr.repos.admin;

import com.fmss.hr.dto.EventDto;
import com.fmss.hr.dto.EventUserDto;
import com.fmss.hr.dto.projection.EventUserProjection;
import com.fmss.hr.entities.Event;
import com.fmss.hr.entities.EventUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventUserRepository extends JpaRepository<EventUser, Long> {
    
    List<EventUser> findEventUsersByEventId(Long id);
    List<EventUser> findAllByUserId(Long id);

    @Query(value=" SELECT e.candidate_id as candidateId,e.date as date,e.end_date as endDate,e.is_online as isOnline,me.start_url as start_url," +
            "ca.first_name as firstName,ca.last_name as lastName,e.status as status" +
            " FROM events e INNER JOIN event_user eu ON eu.user_id= :id AND eu.event_id=e.id " +
            "LEFT JOIN meeting me ON me.id=e.meeting_id left JOIN candidates ca ON ca.id=e.candidate_id WHERE date>CURRENT_DATE ORDER BY date",nativeQuery = true)
    List<EventUserProjection> getEventUsers(Long id, Pageable pageable);

    @Query(value=" SELECT count(*)" +
            " FROM events e INNER JOIN event_user eu ON eu.user_id= :id AND eu.event_id=e.id " +
            "LEFT JOIN meeting me ON me.id=e.meeting_id left JOIN candidates ca ON ca.id=e.candidate_id WHERE date> CURRENT_DATE ",nativeQuery = true)
    int getEventUsersCount(Long id);
}
