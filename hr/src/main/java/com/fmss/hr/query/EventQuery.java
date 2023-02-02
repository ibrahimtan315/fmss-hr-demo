package com.fmss.hr.query;

import com.fmss.hr.dto.EventDto;
import com.fmss.hr.entities.Event;
import com.fmss.hr.mapper.EventMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventQuery {

    private final EntityManager entityManager;
    private final EventMapper eventMapper;

    public EventQuery(EntityManager entityManager, EventMapper eventMapper) {
        this.entityManager = entityManager;
        this.eventMapper = eventMapper;
    }

    public List<EventDto> listEventsByParameters(EventDto eventDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = criteriaQuery.from(Event.class);
        criteriaQuery.select(eventRoot);
        List<Predicate> predicates = new ArrayList<>();

        if(eventDto.getDate() != null && eventDto.getEndDate() != null ){
            Predicate predicateDate = criteriaBuilder.between(eventRoot.get("date").as(LocalDateTime
                .class) , eventDto.getDate() , eventDto.getEndDate());
            predicates.add(predicateDate);
        }else if (eventDto.getDate() !=null ){
            Predicate predicateDate = criteriaBuilder.greaterThan(eventRoot.get("date") , eventDto.getDate());
            predicates.add(predicateDate);
        }else{
            Predicate predicateDate = criteriaBuilder.lessThan(eventRoot.get("date") , eventDto.getEndDate());
            predicates.add(predicateDate);
        }
        if(eventDto.getIsOnline() != null) {
            Predicate predicateIsOnline = criteriaBuilder.equal(eventRoot.get("isOnline"), eventDto.getIsOnline());
            predicates.add(predicateIsOnline);
        }
        if(eventDto.getStatus() != null ) {
            Predicate predicateStatus = criteriaBuilder.equal(eventRoot.get("status"), eventDto.getStatus());
            predicates.add(predicateStatus);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Event> typedQuery = entityManager.createQuery(criteriaQuery);
        List<EventDto> eventDtoList = eventMapper.eventListToEventDtoList(typedQuery.getResultList());

        return eventDtoList;

    }
}
