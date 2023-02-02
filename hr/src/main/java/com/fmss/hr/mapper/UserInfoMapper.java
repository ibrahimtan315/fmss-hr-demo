package com.fmss.hr.mapper;

import com.fmss.hr.dto.UserInfoDto;
import com.fmss.hr.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfoDto userToUserInfoAndPhotoDto(User user);
}
