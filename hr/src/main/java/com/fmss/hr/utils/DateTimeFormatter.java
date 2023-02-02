package com.fmss.hr.utils;





import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Component
public class DateTimeFormatter {

    public LocalDateTime convertStringToDate(String dateString) {
        Instant instant = Instant.parse(dateString);
        ZoneId zone = ZoneId.of("Europe/Istanbul");
        LocalDateTime date = LocalDateTime.ofInstant(instant, zone);
        return date;
    }

    public String convertDateToZonedDate(String dateTime){
        /*ZoneId zone = ZoneId.of("Europe/Istanbul");
        ZonedDateTime zonedDateTime= dateTime.atZone(zone);
        return zonedDateTime;*/
        return dateTime.concat("Z");

    }

    
}
