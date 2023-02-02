package com.fmss.hr.mapper;

import com.fmss.hr.dto.AdvertDto;
import com.fmss.hr.entities.Advert;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdvertMapper {

    Advert advertDtoToAdvert(AdvertDto advertDto);
    AdvertDto advertToAdvertDto(Advert advert);
    List<AdvertDto> advertListToAdvertDtoList(List<Advert> advertList);
}
