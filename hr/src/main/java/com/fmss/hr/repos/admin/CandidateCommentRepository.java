package com.fmss.hr.repos.admin;


import com.fmss.hr.entities.Comment;
import com.fmss.hr.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CandidateCommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByCandidateId(Long candidate_id);

    List<Comment> findAllByUserId(Long user_id);



}
