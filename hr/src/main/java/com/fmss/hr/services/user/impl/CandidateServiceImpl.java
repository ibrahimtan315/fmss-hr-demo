package com.fmss.hr.services.user.impl;

import com.fmss.hr.mapper.CandidateMapper;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.SuggestDto;
import com.fmss.hr.entities.Advert;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.User;
import com.fmss.hr.repos.user.CandidateRepository;
import com.fmss.hr.services.admin.impl.AdvertServiceImpl;
import com.fmss.hr.services.user.CandidateService;
import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("Candidate services")
public class CandidateServiceImpl implements CandidateService {
    private final CandidateMapper candidateMapper;
    private final CandidateRepository candidateRepository;
    private final AdvertServiceImpl advertServiceImpl;
    private final UserService userService;

    public List<CandidateDto> getAllCandidates() {
        return candidateMapper.candidateListToCandidateDtoList(candidateRepository.findAll());
    }

    public List<CandidateDto> getAllByAdvert(Long advertId) {
        return candidateMapper
                .candidateListToCandidateDtoList(candidateRepository
                        .findAllByAdvert(advertServiceImpl.findById(advertId)));}

    public List<CandidateDto> getAllByUser(Long userId) {
        return candidateMapper.
                candidateListToCandidateDtoList(candidateRepository.findAllByUser(userService.getUserById(userId)));}

    public CandidateDto suggestCandidate(SuggestDto suggestDto, String cvPath, Long advertId, Long userId) {

        if (checkIfCandidateExists(suggestDto.getEmail()))
            return null;

        User suggestUser = userService.getUserById(userId); //TODO session eklendiğinde düzenlenecek
        Advert advert =  advertServiceImpl.findById(advertId);

        Candidate candidate = candidateMapper.suggestToCandidate(suggestDto); //TODO cvpath ve advertdan tag verilecek
        candidate.setCvPath(cvPath);
        candidate.setUser(suggestUser);
        candidate.setAdvert(advert);
        candidate.setTag(advert.getJobPosition());
        candidate.setRegisterDate(LocalDateTime.now());
        candidateRepository.save(candidate);
        return candidateMapper.candidateToCandidateDto(candidate);
    }

    public boolean deleteCandidateById(Long userId) {
        if (candidateRepository.findById(userId).isEmpty()) {
            return false;
        }
        candidateRepository.deleteById(userId);
        return true;
    }

    public boolean checkIfCandidateExists(String email) {
        return candidateRepository.findByEmail(email).isPresent();
    }

    public Candidate getCandidateWithoutDtoById(Long candidateId) {
        Optional<Candidate> candidate = candidateRepository.findById(candidateId);
        if (candidate.isPresent()) {
            Candidate requiredCandidate = candidate.get();
            return requiredCandidate;
        }
        return null;
    }

    public CandidateDto getCandidateById(Long candidateId) {
        Optional<Candidate> candidate = candidateRepository.findById(candidateId);
        if (candidate.isPresent()) {
            Candidate requiredCandidate = candidate.get();
            return candidateMapper.candidateToCandidateDto(requiredCandidate);
        }
        return null;
    }

    public CandidateDto updateCandidate(Long id, CandidateDto candidateDto) { //TODO file check veya update daha sonra eklenecek not: test yapılmadı
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if (candidate == null)
            return null;
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setLastName(candidateDto.getLastName());
        candidate.setEmail(candidateDto.getEmail());
        candidate.setPhone(candidateDto.getPhone());
        candidate.setTag(candidateDto.getTag());
        candidateRepository.save(candidate);
        return candidateMapper.candidateToCandidateDto(candidate);
    }

    public String getCandidateFilePath(Long candidateId) {
        return candidateRepository.findById(candidateId).isPresent() ? candidateRepository.findById(candidateId).get().getCvPath() : null;
    }

    @Override
    public int numberOfCandidates() {
        return candidateRepository.numberOfCandidates();
    }
}
