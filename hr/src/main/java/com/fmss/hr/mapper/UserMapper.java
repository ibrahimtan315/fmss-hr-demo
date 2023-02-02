package com.fmss.hr.mapper;

import com.fmss.hr.dto.UserInfoDto;
import com.fmss.hr.dto.response.UserResponse;
import com.fmss.hr.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UserMapper {
    public static UserResponse userToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setIdentityNumber(user.getIdentityNumber());
        userResponse.setBirthday(user.getBirthday().toString());
        userResponse.setDepartment(user.getDepartment().getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setLevel(user.getLevel());
        userResponse.setRole(user.getRole().getName());
        userResponse.setSalary(user.getSalary());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setStatus(user.getStatus());
        userResponse.setStartingDateOfEmployment(user.getStartingDateOfEmployment().toString());
        userResponse.setAddress(user.getAddress());
        userResponse.setTitle(user.getTitle());
        userResponse.setFullName(user.getFullName());
        if(user.getDepartment().getUser() == null){
            userResponse.setManager("Ait olunan departmanın yöneticisi yok");
        }else{
            userResponse.setManager(user.getDepartment().getUser().getFirstName() + " " + user.getDepartment().getUser().getLastName());
        }
        userResponse.setAge(String.valueOf(Period.between(user.getBirthday(), LocalDate.now()).getYears()));
        userResponse.setCity(user.getCity());
        userResponse.setCountry(user.getCountry());
        userResponse.setPostalCode(user.getPostalCode());
        userResponse.setPhotoPath(user.getPhotoPath());
        return userResponse;
    }
    public static UserInfoDto userToUserInfo(User user){
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setFirstName(user.getFirstName());
        userInfoDto.setLastName(user.getLastName());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setTitle(user.getTitle());
        userInfoDto.setPhoneNumber(user.getPhoneNumber());
        userInfoDto.setPhotoPath(user.getPhotoPath());
        userInfoDto.setFullName(user.getFullName());
        return userInfoDto;
    }
}
