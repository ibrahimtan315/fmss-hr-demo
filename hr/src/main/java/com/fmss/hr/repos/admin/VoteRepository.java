package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "select * from vote where user_id= :userId AND survey_id= :surveyId", nativeQuery = true)
    Optional<Vote> voteCheck(Long userId, Long surveyId);
}
