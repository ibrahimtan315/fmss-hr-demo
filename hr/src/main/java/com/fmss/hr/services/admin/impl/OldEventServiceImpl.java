package com.fmss.hr.services.admin.impl;


import com.fmss.hr.common.constant.ExceptionMessages;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.EventDto;
import com.fmss.hr.dto.projection.CandidateProjectionForMeeting;
import com.fmss.hr.dto.projection.ClosestMeetingsProjection;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.Comment;
import com.fmss.hr.entities.Event;
import com.fmss.hr.entities.EventStatus;
import com.fmss.hr.exceptions.CustomException;
import com.fmss.hr.mapper.CandidateMapper;
import com.fmss.hr.repos.admin.EventRepository;
import com.fmss.hr.repos.admin.OldEventRepository;
import com.fmss.hr.repos.admin.CandidateCommentRepository;
import com.fmss.hr.services.admin.EventService;
import com.fmss.hr.services.admin.OldEventService;
import com.fmss.hr.services.user.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OldEventServiceImpl implements OldEventService {

    private EventRepository eventRepository;
    private OldEventRepository oldEventRepository;
    private CandidateCommentRepository candidateCommentRepository;
    private final EventService eventService;
    private final CandidateService candidateService;

    @Autowired
    public OldEventServiceImpl(OldEventRepository oldEventRepository, CandidateCommentRepository candidateCommentRepository, EventService eventService, CandidateService candidateService, EventRepository eventRepository) {
        this.oldEventRepository = oldEventRepository;
        this.candidateCommentRepository = candidateCommentRepository;
        this.eventService = eventService;
        this.candidateService = candidateService;
        this.eventRepository = eventRepository;
    }

    public List<Candidate> getOldEventEmployees(int index){

        Pageable elements = PageRequest.of(index-1, 2);

        List<EventDto> oldEventList;
        List<Candidate> candidateDtoList = new ArrayList<>();
        oldEventList = eventService.listAllEvents(elements);
        oldEventList =  oldEventList.stream().filter(p-> ExpiredEndDate(p)).collect(Collectors.toList());
        oldEventList.forEach(i -> { candidateDtoList.add(candidateService.getCandidateWithoutDtoById(i.getCandidateId()));});

        if(candidateDtoList!=null) {
            return candidateDtoList;
        }
        else{
            throw new CustomException(ExceptionMessages.NO_PENDING_EMPLOYEES, HttpStatus.NOT_FOUND);
        }

    }
    public List<CandidateProjectionForMeeting> getWaitingCandidates(int index){
        Pageable elements= PageRequest.of(index-1,2);

        List<CandidateProjectionForMeeting> candidatesWithoutMeeting =oldEventRepository.getCandidatesWithoutMeeting(elements);


        return candidatesWithoutMeeting;

    }
    public List<Candidate> getAcceptedEmployees(int index){
        Pageable elements = PageRequest.of(index-1, 12);
        List<EventDto> oldEventList;
        oldEventList = eventService.listAllEvents(elements);
        List<Candidate> candidates= new ArrayList<>();
        oldEventList = oldEventList.stream().filter(p->isAccepted(p)).collect(Collectors.toList());
        oldEventList.forEach(i -> { candidates.add(candidateService.getCandidateWithoutDtoById(i.getCandidateId())); });
        if(candidates!=null) {
            return candidates;
        }
        else{
            throw new CustomException(ExceptionMessages.NO_PENDING_EMPLOYEES, HttpStatus.NOT_FOUND);
        }
    }
    public List<Candidate> getRejectedEmployees(int index){
        Pageable elements = PageRequest.of(index-1, 12);
        List<EventDto> oldEventList;
        oldEventList = eventService.listAllEvents(elements);
        List<Candidate> candidates= new ArrayList<>();
        oldEventList = oldEventList.stream().filter(p->isRejected(p)).collect(Collectors.toList());
        oldEventList.forEach(i -> { candidates.add(candidateService.getCandidateWithoutDtoById(i.getCandidateId()));});

        if(candidates!=null) {
            return candidates;
        }
        else{
            throw new CustomException(ExceptionMessages.NO_PENDING_EMPLOYEES, HttpStatus.NOT_FOUND);
        }
    }

    public List<ClosestMeetingsProjection> getClosestMeetings(){
        List<ClosestMeetingsProjection> closestMeetings = oldEventRepository.getClosestEvents();
        return closestMeetings;
    }

    public List<Comment> getCandidateCommentsbyCandidateId(Long candidate_id){
        List<Comment> commentDB= candidateCommentRepository.findAllByCandidateId(candidate_id);
        if(commentDB!=null) {
            return commentDB;
        }
        else {
            throw new CustomException(ExceptionMessages.NO_COMMENTS_FOUND_FOR_CANDIDATE, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public int totalAcceptedCandidateCount() {

        return oldEventRepository.acceptedCandidateCount();
    }
    @Transactional
    public int totalRejectedCandidateCount() {

        return oldEventRepository.rejectedCandidateCount();
    }
    @Transactional
    public int totalHoldingCandidateCount() {

        return oldEventRepository.holdingCandidateCount();
    }
    @Transactional
    public int totalWaitingCandidateCount() {

        return oldEventRepository.waitingCandidateCount();
    }

    public List<Comment> getUserCommentsbyUserId(Long user_id){
        List<Comment> comments= candidateCommentRepository.findAllByUserId(user_id);
        if(comments!=null){
            return comments;
        }
        else {
            throw new CustomException(ExceptionMessages.NO_COMMENTS_FOUND_FOR_USER, HttpStatus.NOT_FOUND);
        }
    }
    public boolean isAccepted(EventDto eventDto){
        if(eventDto.getStatus().equals(EventStatus.ACCEPTED)){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isRejected(EventDto eventDto){
        if(eventDto.getStatus().equals(EventStatus.REJECTED)){
            return true;
        }
        else {
            return false;
        }
    }
    public void setEventStatusAccepted(Long id){

        oldEventRepository.setCandidateStatusAccepted(id);


    }
    public void setEventStatusRejected(Long id){
        oldEventRepository.setCandidateStatusRejected(id);

    }
    public boolean ExpiredEndDate(EventDto eventDto){
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.now();


        if((eventDto.getEndDate().isAfter(currentDate))&&(eventDto.getStatus().equals(EventStatus.PENDING))){

            return false;
        }

        else {
            return true;
        }
    }



}