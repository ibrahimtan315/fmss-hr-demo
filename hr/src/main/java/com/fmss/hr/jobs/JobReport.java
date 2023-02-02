package com.fmss.hr.jobs;

import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.entities.Advert;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.mapper.CandidateMapper;
import com.fmss.hr.services.MailService.MailServiceImpl;
import com.fmss.hr.services.admin.impl.AdvertServiceImpl;
import com.fmss.hr.services.user.CandidateService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobReport {
    private final MailServiceImpl mailServiceImpl;
    private final AdvertServiceImpl advertServiceImpl;
    private final CandidateService candidateService;
    private final CandidateMapper candidateMapper;
    private int count=0;

    public JobReport(MailServiceImpl mailServiceImpl, AdvertServiceImpl advertServiceImpl, CandidateService candidateService, CandidateMapper candidateMapper) {
        this.mailServiceImpl = mailServiceImpl;
        this.advertServiceImpl = advertServiceImpl;
        this.candidateService = candidateService;
        this.candidateMapper = candidateMapper;
    }

    public void sendReportMail() throws MessagingException {
        List<Advert> advertList=  advertServiceImpl.getAllAdvertsForJobService();
        List<CandidateDto> candidateDtoList=  candidateService.getAllCandidates();
        List<Candidate> candidateList = candidateMapper.candidateDtoListToCandidateEntityList(candidateDtoList);

        candidateList.stream().forEach(p->AreThereNewCandidates(p));
        mailServiceImpl.sendReportMail(count,advertList);

    }
    public boolean AreThereNewCandidates(Candidate candidate){
        LocalDateTime currentDate= LocalDateTime.now();
        LocalDateTime date= currentDate.minusDays(7);

        LocalDateTime registerDate = candidate.getRegisterDate();

        if(registerDate.isAfter(date)){
            count=count +1;
            return true;

        }
        else {
            return false;
        }
    }

}
