package com.fmss.hr.services.user.impl;

import com.fmss.hr.common.constant.ExceptionMessages;
import com.fmss.hr.configuration.security.JwtTokenProvider;
import com.fmss.hr.dto.BirthdayEventDto;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.UserInfoDto;
import com.fmss.hr.dto.projection.BirthdayEventProjection;
import com.fmss.hr.dto.projection.CandidateProjection;
import com.fmss.hr.dto.request.LoginRequest;
import com.fmss.hr.dto.request.SignUpRequest;
import com.fmss.hr.dto.request.UserRequest;
import com.fmss.hr.dto.response.LoginResponse;
import com.fmss.hr.dto.response.UserResponse;
import com.fmss.hr.entities.*;
import com.fmss.hr.exceptions.CustomException;
import com.fmss.hr.mapper.UserMapper;
import com.fmss.hr.repos.admin.DepartmentRepository;
import com.fmss.hr.repos.user.RoleRepository;
import com.fmss.hr.repos.user.UserRepository;
import com.fmss.hr.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${resourcesPath}")
    private String resourcesPath;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new ArrayList<>();
        for (User user: users) {
            userResponse.add(UserMapper.userToUserResponse(user));
        }
        return userResponse;
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public User updateUser(Long userId, UserRequest newUser, String photoPath){
        final var roleEntity = findRoleByRoleName(newUser.getRole());
        final var departmentEntity = findDepartmentByDepartmentName(newUser.getDepartment());
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionMessages.USER_NOT_FOUND,HttpStatus.NOT_FOUND));
        foundUser.setFirstName(newUser.getFirstName());
        foundUser.setLastName(newUser.getLastName());
        foundUser.setFullName(newUser.getFirstName() + " " + newUser.getLastName());
        foundUser.setBirthday(newUser.getBirthday());
        foundUser.setIdentityNumber(newUser.getIdentityNumber());
        foundUser.setEmail(newUser.getEmail());
        if(newUser.getPassword() != null){
            foundUser.setPassword(encodePassword(newUser.getPassword()));
        }
        foundUser.setStatus(newUser.getStatus());
        foundUser.setLevel(newUser.getLevel());
        foundUser.setSalary(newUser.getSalary());
        foundUser.setStartingDateOfEmployment(newUser.getStartingDateOfEmployment());
        foundUser.setPhoneNumber(newUser.getPhoneNumber());
        foundUser.setRole(roleEntity);
        foundUser.setDepartment(departmentEntity);
        foundUser.setTitle(newUser.getTitle());
        foundUser.setAddress(newUser.getAddress());
        foundUser.setCity(newUser.getCity());
        foundUser.setCountry(newUser.getCountry());
        foundUser.setPostalCode(newUser.getPostalCode());
        foundUser.setPhotoPath(photoPath);
        return userRepository.save(foundUser);
    }

    public void deleteById(Long userId){
        userRepository.deleteById(userId);
    }

    public List<CandidateDto> myCandidates(Long userId){
        List<CandidateDto> result =new ArrayList<>();
        List<CandidateProjection> candidateProjections = userRepository.myCandidates(userId);
        candidateProjections.forEach
                (a-> result.add(new CandidateDto(a.getFirstName(), a.getLastName(), a.getEmail(), a.getPhone(), a.getTag())));
        return result ;
    }

    @Override
    public int numberOfUsers() {
        return userRepository.numberOfUsers();
    }

    @Override
    public int numberOfPassiveUsers() {
        return userRepository.numberOfPassiveUsers();
    }

    @Override
    public int numberOfFilteredAndPassiveUsers(String firstName, String lastName, String title) {
        return userRepository.findAllByStatusIsFalseAndFirstNameContainingIgnoreCaseOrStatusIsFalseAndLastNameContainingIgnoreCaseOrStatusIsFalseAndTitleContainingIgnoreCase(firstName, lastName, title).size();
    }

    @Override
    public int numberOfFilteredUsers(String firstName, String lastName, String title) {
        return userRepository.findAllByStatusIsTrueAndFirstNameContainingIgnoreCaseOrStatusIsTrueAndLastNameContainingIgnoreCaseOrStatusIsTrueAndTitleContainingIgnoreCase(firstName, lastName, title).size();
    }

    @Override
    public void makePassive(Long userId) {
        userRepository.makePassive(userId);
    }

    @Override
    public void makeActive(Long userId) {
        userRepository.makeActive(userId);
    }

    @Override
    public List<UserResponse> getAllByStatusIsTrue(int index, String filter) {
        Pageable elements = PageRequest.of(index - 1, 8);
        List<User> users = userRepository.findAllByStatusIsTrueAndFirstNameContainingIgnoreCaseOrStatusIsTrueAndLastNameContainingIgnoreCaseOrStatusIsTrueAndTitleContainingIgnoreCase(filter, filter, filter, elements);
        List<UserResponse> userResponse = new ArrayList<>();
        for (User user: users) {
            userResponse.add(UserMapper.userToUserResponse(user));
        }
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllByStatusIsFalse(int index, String filter) {
        Pageable elements = PageRequest.of(index - 1, 8);
        List<User> users = userRepository.findAllByStatusIsFalseAndFirstNameContainingIgnoreCaseOrStatusIsFalseAndLastNameContainingIgnoreCaseOrStatusIsFalseAndTitleContainingIgnoreCase(filter, filter, filter, elements);
        List<UserResponse> userResponse = new ArrayList<>();
        for (User user: users) {
            userResponse.add(UserMapper.userToUserResponse(user));
        }
        return userResponse;
    }

    @Override
    public int myCandidatesCount(Long userId) {
        return userRepository.myCandidatesCount(userId);
    }

    @Override
    public List<BirthdayEventDto> getBirthdayEventList() {
        List<BirthdayEventDto> result = new ArrayList<>();
        List<BirthdayEventProjection> birthdayEventProjections = userRepository.getBirthdayEvent();

        birthdayEventProjections.forEach
                (a-> result.add(new BirthdayEventDto(a.getFirstName(), a.getLastName(), a.getPhotoPath(), a.getBirthday(),a.getAge())));
        return result;
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        final String email = loginRequest.getEmail();
        final String password = loginRequest.getPassword();

        LocalDate loginDate = LocalDate.now();

        try {
            final User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

            if(Boolean.FALSE.equals(user.getStatus())) {
                return null;
            }

            user.setLastLoginDate(loginDate);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final String token = jwtTokenProvider.createToken(email, user.getRole(), user.getId());
            return new LoginResponse(token, user.getRole().getName(), user.getId());
        } catch (AuthenticationException authenticationException) {
            throw new CustomException(ExceptionMessages.INVALID_USERNAME_OR_PASSWORD_SUPPLIED, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {
        final var roleEntity = findRoleByRoleName(signUpRequest.getRole());
        final var departmentEntity = findDepartmentByDepartmentName(signUpRequest.getDepartment());

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setFullName(signUpRequest.getFirstName() + " " + signUpRequest.getLastName());
        user.setIdentityNumber(signUpRequest.getIdentityNumber());
        user.setEmail(signUpRequest.getEmail());
        user.setBirthday(signUpRequest.getBirthday());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setStartingDateOfEmployment(signUpRequest.getStartingDateOfEmployment());
        user.setPassword(encodePassword(signUpRequest.getPassword()));
        user.setRole(roleEntity);
        user.setStatus(signUpRequest.getStatus());
        user.setLevel(signUpRequest.getLevel());
        user.setSalary(signUpRequest.getSalary());
        user.setDepartment(departmentEntity);
        user.setAddress(signUpRequest.getAddress());
        user.setTitle(signUpRequest.getTitle());
        user.setCity(signUpRequest.getCity());
        user.setCountry(signUpRequest.getCountry());
        user.setPostalCode(signUpRequest.getPostalCode());
        user.setStatus(true);
        List<Task> tasks = new ArrayList<Task>();
        user.setTasks(tasks);
        if(existsUserByEmail(signUpRequest.getEmail())){
            userRepository.save(user);
            return user.getId();
        }
        return null;
    }

    @Override
    @Transactional
    public boolean existsUserByEmail(String email) {
        return !userRepository.existsUserByEmail(email);
    }

    private Role findRoleByRoleName(String role){
        return roleRepository.findByName(role)
                .orElseThrow(() -> new CustomException(ExceptionMessages.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Department findDepartmentByDepartmentName(String department){
        return departmentRepository.findByName(department)
                .orElseThrow(() -> new CustomException(ExceptionMessages.DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public String userProfilePhotosSave(MultipartFile file, String oldPhotoPath) {
        try {
            UUID uuid = UUID.randomUUID();
            String fileName;

            if(oldPhotoPath != null){
                fileName = oldPhotoPath;
            }else{
                fileName = uuid.toString() + ".jpg";
            }
            var inputFile = file.getInputStream();
            if(inputFile.available() > 50){
                Files.copy(file.getInputStream(), Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/photos").resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            }
            return fileName;
        }
        catch (Exception e) {
            throw new RuntimeException("Could not store the file " + e.getMessage() );
        }
    }

    public Resource getFileByName(String fileName) {
        return getFileByPath(String.valueOf(Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/photos")+ "/" +fileName));
    }

    public Resource getFileByPath(String path) {
        try {
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                //file = Paths.get(System.getProperty("user.dir"),"hr/src/main/resources/uploads/userProfilePhotos/default.png");
                file = Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/photos/default.png");
                return new UrlResource(file.toUri());
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllPageable(int index) {
        Pageable elements = PageRequest.of(index - 1, 8);
        return userRepository.findAllByRoleId(2, elements);
    }

    @Override
    public List<UserInfoDto> getAllUsersInfo(int index, String filter) {
            Pageable elements = PageRequest.of(index - 1, 8);
            List<User> users = userRepository.findAllByStatusIsTrueAndFirstNameContainingIgnoreCaseOrStatusIsTrueAndLastNameContainingIgnoreCaseOrStatusIsTrueAndTitleContainingIgnoreCase(filter, filter, filter, elements);
            List<UserInfoDto> userInfoDtoList = new ArrayList<>();
            for(User user: users){
                userInfoDtoList.add(UserMapper.userToUserInfo(user));
            }
            return userInfoDtoList;
    }

    @Override
    public Boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return false;
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), oldPassword));
            user.setPassword(encodePassword(newPassword));
            userRepository.save(user);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }
}