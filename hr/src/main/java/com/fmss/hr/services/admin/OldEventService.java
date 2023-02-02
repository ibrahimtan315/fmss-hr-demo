package com.fmss.hr.services.admin;

import com.fmss.hr.dto.projection.CandidateProjectionForMeeting;
import com.fmss.hr.dto.projection.ClosestMeetingsProjection;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.Comment;

import java.util.List;

public interface OldEventService {
    List<Candidate> getOldEventEmployees(int index);
    List<Candidate> getAcceptedEmployees(int index);
    List<Candidate> getRejectedEmployees(int index);
    List<Comment> getCandidateCommentsbyCandidateId(Long candidate_id);
    List<Comment> getUserCommentsbyUserId(Long user_id);
    void setEventStatusAccepted(Long id);
    void setEventStatusRejected(Long id);
    List<CandidateProjectionForMeeting> getWaitingCandidates(int index);
    List<ClosestMeetingsProjection> getClosestMeetings();
    int totalHoldingCandidateCount();
    int totalWaitingCandidateCount();
    int totalAcceptedCandidateCount();
    int totalRejectedCandidateCount();



}