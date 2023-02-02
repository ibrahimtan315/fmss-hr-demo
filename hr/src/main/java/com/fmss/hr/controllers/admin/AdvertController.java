package com.fmss.hr.controllers.admin;

import com.fmss.hr.dto.AdvertDto;
import com.fmss.hr.dto.request.AdvertRequest;
import com.fmss.hr.entities.Advert;
import com.fmss.hr.services.admin.impl.AdvertServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/adverts")
@CrossOrigin(origins = "*")
public class AdvertController {
    private final AdvertServiceImpl advertServiceImpl;

    @RequestMapping(value="/{isActive}/{pageNum}", method = RequestMethod.GET)
    public ResponseEntity<List<AdvertDto>> getAllAdvertsWithStatus(@PathVariable int pageNum, @PathVariable Boolean isActive, @RequestParam(value="title", required = false) String title) {
        List<AdvertDto> advertDtoList = advertServiceImpl.getAllAdvertsWithStatus(pageNum, isActive, title);
        if (advertDtoList != null)
            return ResponseEntity.status(HttpStatus.OK).body(advertDtoList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(value = "/getAllActive", method = RequestMethod.GET)
    public ResponseEntity<List<AdvertDto>> getAllActiveAdverts() {
        List<AdvertDto> advertDtoList = advertServiceImpl.getAllActiveAdverts();
        if (advertDtoList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(advertDtoList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(value = "/{isActive}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getAdvertCountWithStatus(@PathVariable Boolean isActive) {
        return ResponseEntity.status(HttpStatus.OK).body(advertServiceImpl.totalAdvertCountWithStatus(isActive));
    }

    @RequestMapping(method = RequestMethod.GET)//TODO TİTLE KULLANILMAMIŞ
    public ResponseEntity<Integer> getAdvertCount(@RequestParam(value="title") String title) {
        return ResponseEntity.status(HttpStatus.OK).body(advertServiceImpl.totalAdvertCount());
    }

    @DeleteMapping("/deleteAdvert/{advertId}")
    public ResponseEntity<String> deleteAdvertById(@PathVariable Long advertId) {
       if (!advertServiceImpl.deleteAdvertById(advertId))
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find the advert");
        return ResponseEntity.status(HttpStatus.OK).body("Advert successfully deleted");
    }

    @RequestMapping(value = "/updateAdvert/{advertId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdvertDto> updateAdvert(@PathVariable Long advertId, @RequestBody Advert newAdvert){
        AdvertDto advertDto = advertServiceImpl.updateAdvert(advertId, newAdvert);
        if(advertDto != null)
            return ResponseEntity.status(HttpStatus.OK).body(advertDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @RequestMapping(value = "/createAdvert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAdvert(@Valid @RequestBody AdvertRequest advertRequest){
        boolean check = advertServiceImpl.createAdvert(advertRequest);
        if(check)
            return ResponseEntity.status(HttpStatus.OK).body("Advert successfully created!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}