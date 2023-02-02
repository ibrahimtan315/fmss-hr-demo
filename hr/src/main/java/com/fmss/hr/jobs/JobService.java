package com.fmss.hr.jobs;

import com.fmss.hr.entities.Advert;
import com.fmss.hr.repos.admin.AdvertRepository;
import com.fmss.hr.services.user.impl.TimeSheetServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

@Service
public class JobService {
    private final AdvertRepository advertRepository;
    private final JobReport jobReport;
    private final TimeSheetServiceImpl timeSheetService;
    Logger log = LoggerFactory.getLogger(JobService.class);

    public JobService(AdvertRepository advertRepository, JobReport jobReport, TimeSheetServiceImpl timeSheetService) {
        this.advertRepository = advertRepository;
        this.jobReport = jobReport;
        this.timeSheetService = timeSheetService;
    }

    /*@Scheduled(cron="0 0 * * * *")
    public void jobScheduled1(){
        List<Advert> advert= advertRepository.findAll();
        System.out.println("every hour call in " + new Date().toString());
        System.out.println("fetch"+advert.size());
        log.info("advert : {} ",advert);
    }
    @Scheduled(cron = "0 0 12 1/1 * *")
    public void jobScheduled2(){
        List<Advert> advert= advertRepository.findAll();
        System.out.println("every day call in"+new Date().toString());
        System.out.println("fetch"+advert.size());
        log.info("advert : {} ",advert);
    }*/

    @Scheduled(cron = "0 0 12 * * MON")
    public void jobScheduled3() throws MessagingException {
        //Her pazartesi çalışıcak rapor maili
        jobReport.sendReportMail();
    }

    @Scheduled(cron = "0 0 1 1/1 * *") //saat gece 1de çalışıyor (3. 1 o demek)
    //@Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void sheetJob() {
        //her gün sabah 9'da timeSheet yenileme kontrolü
        timeSheetService.checkTimeSheetForAll();
    }

    
}
