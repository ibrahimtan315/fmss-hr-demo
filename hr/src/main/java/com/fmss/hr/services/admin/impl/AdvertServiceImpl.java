package com.fmss.hr.services.admin.impl;
import com.fmss.hr.dto.AdvertDto;
import com.fmss.hr.dto.request.AdvertRequest;
import com.fmss.hr.mapper.AdvertMapper;
import com.fmss.hr.repos.admin.AdvertRepository;
import com.fmss.hr.entities.Advert;
import com.fmss.hr.services.admin.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Qualifier("Advert services")
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;

    private final AdvertMapper advertMapper;

    public List<Advert> getAllAdvertsForJobService() {
        return advertRepository.findAll();
    }

    public List<AdvertDto> getAllActiveAdverts() {
        return advertMapper.advertListToAdvertDtoList(advertRepository.findAllByStatusIsTrue());
    }

    @Transactional
    public List<AdvertDto> getAllAdvertsWithStatus(int index, Boolean isActive, String title) {
        Pageable elements = PageRequest.of(index-1, 6);
        if (isActive && title.length() == 0) {
            List<Advert> allElements = advertRepository.findAllByStatus(isActive, elements);
            List<AdvertDto> elementDtoList = new ArrayList<>();
            allElements.forEach(advert -> elementDtoList.add(advertMapper.advertToAdvertDto(advert)));
            return elementDtoList;
        } else if (isActive && title.length() > 0) {
            List<AdvertDto> filteredElements = new ArrayList<>();
            int totalAdverts = totalAdvertCountWithStatus(isActive)/6;
            int limit;

            if (totalAdverts >= 1) {
                limit = totalAdverts;
            } else {
                limit = 6;
            }
            for (int i = 0; i < limit; i++) {

                if (filteredElements.size() == 6) {
                    return filteredElements;
                }

                elements = PageRequest.of(i+index-1, 6);
                List<Advert> allElements = advertRepository.findAllByStatusAndTitleContainingIgnoreCase(isActive, title, elements);
                List<AdvertDto> elementDtoList = new ArrayList<>();
                allElements.forEach(advert -> elementDtoList.add(advertMapper.advertToAdvertDto(advert)));
                filteredElements.addAll(elementDtoList);
            }
            return filteredElements;
        }

        if (title.length() == 0) {
            Page<Advert> allElements = advertRepository.findAll(elements);
            List<Advert> elementList = allElements.getContent();
            List<AdvertDto> elementDtoList = new ArrayList<>();
            elementList.forEach(advert -> {
                elementDtoList.add(advertMapper.advertToAdvertDto(advert));
            });
            return elementDtoList; //TODO true ise true döndürür, false ise hepsini
        } else if (title.length() > 0) {
            List<AdvertDto> filteredElements = new ArrayList<>();

            int totalAdverts = totalAdvertCount()/6;
            int limit;

            if (totalAdverts >= 1) {
                limit = totalAdverts;
            } else {
                limit = 6;
            }

            for (int i = 0; i < limit; i++) {

                if (filteredElements.size() == 6) {
                    return filteredElements;
                }
                elements = PageRequest.of(i+index-1, 6);
                List<Advert> allElements = advertRepository.findAllByTitleContainingIgnoreCase(title, elements);
                List<AdvertDto> elementDtoList = new ArrayList<>();
                allElements.forEach(advert -> {
                    elementDtoList.add(advertMapper.advertToAdvertDto(advert));
                });

                filteredElements.addAll(elementDtoList);
            }
            return filteredElements;
        }
        return null;
    }

    @Transactional
    public int totalAdvertCountWithStatus(Boolean status) {
        if (!status) {
            return advertRepository.advertCount();
        } else {
            return advertRepository.advertCountWithStatus(status);
        }
    }

    @Transactional
    public int totalAdvertCount() {

        return advertRepository.advertCount();
    }

    public boolean deleteAdvertById(Long id) {
        if (advertRepository.findById(id).isEmpty())
            return false;
        this.advertRepository.deleteById(id);
        return true;
    }
    @Transactional
    public boolean createAdvert(AdvertRequest advertRequest){
        if (checkIfAdvertExists(advertRequest.getTitle())){
            return false;
        }
        Advert advert = new Advert();
        advert.setStatus(true);
        advert.setTitle(advertRequest.getTitle());
        advert.setDescription(advertRequest.getDescription());
        advert.setJobPosition(advertRequest.getJobPosition());
        advert.setMannerOfWork(advertRequest.getMannerOfWork());
        advert.setDepartment(advertRequest.getDepartment());

        if (advert.getTitle().length() >=3) { //TODO manuel çözüm düzeltilecek
            advertRepository.save(advert);
            return true;
        }
        return false;
    }

    public AdvertDto updateAdvert(Long id, Advert advert){
        Advert advertObj = advertRepository.findById(id).orElse(null);
        if(advertObj != null){
            advertObj.setTitle(advert.getTitle());
            advertObj.setJobPosition(advert.getJobPosition());
            advertObj.setMannerOfWork(advert.getMannerOfWork());
            advertObj.setDescription(advert.getDescription());
            advertObj.setDepartment(advert.getDepartment());
            advertObj.setStatus(advert.getStatus());
            advertRepository.save(advertObj);
            return advertMapper.advertToAdvertDto(advertObj);
        }
        return null;
    }

    public boolean checkIfAdvertExists(String advertName) {
        return advertRepository.findByTitle(advertName).isPresent();
    }

    public Advert findById(Long advertId) {
        return advertRepository.findById(advertId).orElse(null);
    }
}

