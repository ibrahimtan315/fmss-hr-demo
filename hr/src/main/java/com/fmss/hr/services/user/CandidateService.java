package com.fmss.hr.services.user;

import com.fmss.hr.mapper.CandidateMapper;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.SuggestDto;
import com.fmss.hr.entities.Candidate;

import java.util.List;

public interface CandidateService {

    List<CandidateDto> getAllCandidates();
    List<CandidateDto> getAllByAdvert(Long advertId);
    List<CandidateDto> getAllByUser(Long userId);
    CandidateDto suggestCandidate(SuggestDto suggestDto, String cvPath, Long advertId, Long userId);
    boolean deleteCandidateById(Long userId);
    boolean checkIfCandidateExists(String email);
    CandidateDto getCandidateById(Long candidateId);
    Candidate getCandidateWithoutDtoById(Long candidateId);
    CandidateDto updateCandidate(Long id, CandidateDto candidateDto);
    String getCandidateFilePath(Long candidateId);
    int numberOfCandidates();
}
