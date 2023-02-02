package com.fmss.hr.controllers.MailController;

import com.fmss.hr.services.MailService.MailService;
import com.fmss.hr.services.MailService.MailServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {


    private final MailService mailService;

    public MailController(com.fmss.hr.services.MailService.MailService mailService) {

        this.mailService = mailService;
    }
    @GetMapping("/send-mail-to-candidate/{id}")
    public void sendMailToCandidate(@PathVariable Long id) throws MessagingException {
        mailService.sendMailToCandidate(id);
    }
    @GetMapping("/send-mail-to-user/{id}")
    public void sendMailToUser(@PathVariable Long id) throws MessagingException {
        mailService.sendMailToUser(id);

    }
}
