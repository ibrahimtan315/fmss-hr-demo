package com.fmss.hr.services.MailService;

import com.fmss.hr.entities.Advert;
import com.fmss.hr.entities.Candidate;
import com.fmss.hr.entities.User;
import com.fmss.hr.repos.user.CandidateRepository;
import com.fmss.hr.repos.user.UserRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@EnableAutoConfiguration
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public MailServiceImpl(JavaMailSender mailSender, CandidateRepository candidateRepository, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    public String getCandidateNameByid(Long id){

        Candidate candidate=candidateRepository.findById(id).get();
        return candidate.getFirstName();
    }
    public String getCandidateSurnameByid(Long id){
        Candidate candidate=candidateRepository.findById(id).get();
        return candidate.getLastName();

    }
    public String getCandidateEmailById(Long id){
        Candidate candidate=candidateRepository.findById(id).get();
        return candidate.getEmail();
    }
    public String getUserNameByid(Long id){

        User user=userRepository.findById(id).get();
        return user.getFirstName();
    }
    public String getUserSurnameByid(Long id){
        User user=userRepository.findById(id).get();
        return user.getLastName();

    }
    public String getUserEmailById(Long id){
        User user=userRepository.findById(id).get();
        return user.getEmail();
    }


    public void sendMailToCandidate(Long id) throws MessagingException {

        String name=getCandidateNameByid(id);
        String surname=getCandidateSurnameByid(id);
        String email=getCandidateEmailById(id);
        String to=email;
        String body="Merhabalar sayın "+name +" "+surname ;
        String subject="FMSS Bilisim";
        MimeMessage mimeMessage1= mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage1,true);
        mimeMessageHelper.setFrom("springemailsendingtutorial1@gmail.com");
        mimeMessageHelper.setTo(to);
        body+="<hr><img src='cid:logoImage' />";
        mimeMessageHelper.setText(body,true);

        FileSystemResource resource= new FileSystemResource("hr/src/main/resources/fmss.png");
        mimeMessageHelper.addInline("logoImage",resource);
        mimeMessageHelper.setSubject(subject);
        mailSender.send(mimeMessage1);
        System.out.println("Mail Sent...");
}
    public void sendMailToUser(Long id) throws MessagingException {

        String name=getUserNameByid(id);
        String surname=getUserSurnameByid(id);
        String email=getUserEmailById(id);
        String to=email;
        String body="Merhabalar sayın "+name +" "+surname ;
        String subject="FMSS Bilisim";
        MimeMessage mimeMessage1= mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage1,true);
        mimeMessageHelper.setFrom("springemailsendingtutorial1@gmail.com");
        mimeMessageHelper.setTo(to);
        body+="<hr><img src='cid:logoImage' />";
        mimeMessageHelper.setText(body,true);


        FileSystemResource resource= new FileSystemResource("hr/src/main/resources/fmss.png");
        mimeMessageHelper.addInline("logoImage",resource);
        mimeMessageHelper.setSubject(subject);
        mailSender.send(mimeMessage1);
        System.out.println("Mail Sent...");
    }
    public void sendReportMail(int newCandidateNum, List<Advert> adverts) throws MessagingException {

        String email="berkant.kaptan@hotmail.com"; //admin maili girilicek
        String to=email;
        String body="Bu hafta cv'si eklenmis aday sayısı: "+newCandidateNum+ ".\n"+ "Suan acık olan ilanlar: "+ adverts ;
        String subject="FMSS Bilisim Weekly Report";
        MimeMessage mimeMessage1= mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage1,true);
        mimeMessageHelper.setFrom("springemailsendingtutorial1@gmail.com");
        mimeMessageHelper.setTo(to);
        body+="<hr><img src='cid:logoImage' />";
        mimeMessageHelper.setText(body,true);


        FileSystemResource resource= new FileSystemResource("hr/src/main/resources/fmss.png");
        mimeMessageHelper.addInline("logoImage",resource);
        mimeMessageHelper.setSubject(subject);
        mailSender.send(mimeMessage1);
        System.out.println("Mail Sent...");
    }
}
