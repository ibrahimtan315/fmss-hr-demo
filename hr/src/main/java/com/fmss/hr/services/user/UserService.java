package com.fmss.hr.services.user;

import com.fmss.hr.dto.BirthdayEventDto;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.UserInfoDto;
import com.fmss.hr.dto.request.LoginRequest;
import com.fmss.hr.dto.request.SignUpRequest;
import com.fmss.hr.dto.request.UserRequest;
import com.fmss.hr.dto.response.LoginResponse;
import com.fmss.hr.dto.response.UserResponse;
import com.fmss.hr.entities.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    LoginResponse login(LoginRequest loginRequest);
    Long signUp(SignUpRequest signUpRequest);
    boolean existsUserByEmail(String email);
    List<UserResponse> getAllUsers();
    User getUserById(Long userId);
    User updateUser(Long userId, UserRequest newUser, String photoPath);
    void deleteById(Long userId);
    List<CandidateDto> myCandidates(Long userId);
    int numberOfUsers();
    void makePassive(Long userId);
    void makeActive(Long userId);
    List<UserResponse> getAllByStatusIsTrue(int index, String filter);
    List<UserResponse> getAllByStatusIsFalse(int index, String filter);
    int myCandidatesCount(Long userId);
    List<BirthdayEventDto> getBirthdayEventList();
    String userProfilePhotosSave(MultipartFile file, String oldPhotoPath);
    Resource getFileByName(String fileName);
    Resource getFileByPath(String path);
    List<User> getAllPageable(int index);
    List<UserInfoDto> getAllUsersInfo(int index, String filter);
    int numberOfPassiveUsers();
    int numberOfFilteredAndPassiveUsers(String firstName, String lastName, String title);
    int numberOfFilteredUsers(String firstName, String lastName, String title);
    Boolean changePassword(Long userId, String oldPassword, String newPassword);
}
