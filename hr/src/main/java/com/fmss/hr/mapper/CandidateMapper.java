package com.fmss.hr.mapper;

import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.SuggestDto;
import com.fmss.hr.entities.Candidate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    Candidate DtoToCandidate(CandidateDto candidateDto);
    CandidateDto candidateToCandidateDto(Candidate candidate);
    SuggestDto suggestToDto(SuggestDto suggestDto);
    CandidateDto dtoToSuggest(CandidateDto candidateDto);
    Candidate suggestToCandidate(SuggestDto suggestDto);
    List<CandidateDto> candidateListToCandidateDtoList(List<Candidate> list);
    List<Candidate> candidateDtoListToCandidateEntityList(List<CandidateDto> list);


}