package com.fmss.hr.services.admin;

import com.fmss.hr.dto.AdvertDto;
import com.fmss.hr.dto.request.AdvertRequest;
import com.fmss.hr.entities.Advert;

import java.util.List;

public interface AdvertService {

    List<Advert> getAllAdvertsForJobService();
    boolean deleteAdvertById(Long id);
    boolean createAdvert(AdvertRequest advertRequest);
    AdvertDto updateAdvert(Long id, Advert advert);
    boolean checkIfAdvertExists(String advertName);
}
