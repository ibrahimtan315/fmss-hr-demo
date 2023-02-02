package com.fmss.hr.query;

import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.EventDto;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.mapper.CandidateMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class CandidateQuery {

    private final EntityManager entityManager;
    private final CandidateMapper candidateMapper;

    public CandidateQuery(EntityManager entityManager, CandidateMapper candidateMapper) {
        this.entityManager = entityManager;
        this.candidateMapper = candidateMapper;
    }

    public List<CandidateDto> ListCandidateByParameters(CandidateDto candidateDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Candidate> criteriaQuery = criteriaBuilder.createQuery(Candidate.class);
        Root<Candidate> candidateRoot = criteriaQuery.from(Candidate.class);
        criteriaQuery.select(candidateRoot);
        List<Predicate> predicates = new ArrayList<>();

        if(candidateDto.getTag() != null) {
            Predicate predicateTag = criteriaBuilder.equal(candidateRoot.get("tag") , candidateDto.getTag());
            predicates.add(predicateTag);
        }

        if(candidateDto.getAdvertDto() != null) {
            Predicate predicateAdvert = criteriaBuilder.equal(candidateRoot.get("advert_id") , candidateDto.getAdvertDto().getId());
            predicates.add(predicateAdvert);
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Candidate> typedQuery = entityManager.createQuery(criteriaQuery);
        List<CandidateDto> candidateDtoList = candidateMapper.candidateListToCandidateDtoList(typedQuery.getResultList());

        return candidateDtoList;
    }
}
