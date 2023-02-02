package com.fmss.hr.repos.admin;


import com.fmss.hr.dto.projection.CandidateProjection;
import com.fmss.hr.dto.projection.CandidateProjectionForMeeting;
import com.fmss.hr.dto.projection.ClosestMeetingsProjection;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OldEventRepository extends JpaRepository<Event,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE events SET status = 'REJECTED' WHERE candidate_id= :candidateId", nativeQuery = true)
    void setCandidateStatusRejected(Long candidateId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE events SET status = 'ACCEPTED' WHERE candidate_id= :candidateId", nativeQuery = true)
    void setCandidateStatusAccepted(Long candidateId);

    @Query(value="SELECT ca.id as id,ca.cv_path as cvPath,ca.email as email,ca.first_name as firstName,ca.last_name as lastName,u.first_name as userFirstName,u.last_name as userLastName,ca.phone as phone,ca.register_date as registerDate,ca.reference_id as referenceId,ca.tag as tag,ca.advert_id as advertId \n" +
            "FROM candidates ca left JOIN events e ON ca.id = e.candidate_id\n JOIN users u ON ca.reference_id=u.id " +
            "WHERE e.candidate_id IS NULL", nativeQuery = true)
    List<CandidateProjectionForMeeting> getCandidatesWithoutMeeting(Pageable pageable);

    @Query(value="SELECT e.candidate_id as candidateId,e.date as date,ca.first_name as firstName,ca.last_name as lastName FROM events e left JOIN candidates ca ON e.candidate_id=ca.id WHERE date > CURRENT_DATE ORDER BY date LIMIT 3",nativeQuery = true)
    List<ClosestMeetingsProjection> getClosestEvents();

    @Query(value = "SELECT count(*) FROM candidates ca left JOIN events e ON ca.id=e.candidate_id WHERE e.status='ACCEPTED'", nativeQuery = true)
    int acceptedCandidateCount();

    @Query(value = "SELECT count(*) FROM candidates ca left JOIN events e ON ca.id=e.candidate_id WHERE e.status='REJECTED'", nativeQuery = true)
    int rejectedCandidateCount();

    @Query(value= "SELECT count(*) FROM candidates ca left JOIN events e ON ca.id = e.candidate_id WHERE e.candidate_id IS NULL", nativeQuery = true)
    int waitingCandidateCount();

    @Query(value= "SELECT count(*) FROM candidates ca left JOIN events e ON ca.id = e.candidate_id WHERE end_date < CURRENT_DATE AND e.status='PENDING'", nativeQuery = true)
    int holdingCandidateCount();

    @Query(value="SELECT count(*) FROM events WHERE date > CURRENT_DATE ",nativeQuery = true)
    int getClosestEventCount();


}
