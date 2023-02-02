package com.fmss.hr.dto.projection;

import java.time.LocalDateTime;

public interface BirthdayEventProjection {
    String getFirstName();
    String getLastName();
    String getPhotoPath();
    int getAge();
    LocalDateTime getBirthday();
}
