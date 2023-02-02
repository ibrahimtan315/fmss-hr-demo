package com.fmss.hr.repos.user;

import com.fmss.hr.dto.response.UserResponse;
import com.fmss.hr.entities.Advert;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

    Optional<Candidate> findByEmail(String email);

    List<Candidate> findAllByAdvert(Advert advert);

    List<Candidate> findAllByUser(User user);

    @Query(value = "SELECT count(*) FROM candidates", nativeQuery = true)
    int numberOfCandidates();
}
