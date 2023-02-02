package com.fmss.hr.dto.projection;

import java.time.LocalDate;
import java.util.Date;

public interface CandidateProjection {

     String getFirstName();
     String getLastName();
     String getEmail();
     String getPhone();
     String getTag();
     LocalDate getBirthday();

}
