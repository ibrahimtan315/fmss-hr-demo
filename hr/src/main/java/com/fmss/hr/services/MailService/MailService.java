package com.fmss.hr.services.MailService;

import com.fmss.hr.entities.Advert;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {
    void sendMailToCandidate(Long id) throws MessagingException;
    void sendMailToUser(Long id) throws MessagingException;
    void sendReportMail(int newCandidateNum, List<Advert> adverts) throws MessagingException;
}
