package com.fmss.hr.controllers.user;

import com.fmss.hr.dto.BirthdayEventDto;
import com.fmss.hr.dto.CandidateDto;
import com.fmss.hr.dto.UserInfoDto;
import com.fmss.hr.dto.request.LoginRequest;
import com.fmss.hr.dto.request.SignUpRequest;
import com.fmss.hr.dto.request.UserRequest;
import com.fmss.hr.dto.response.LoginResponse;
import com.fmss.hr.dto.response.UserResponse;
import com.fmss.hr.entities.User;
import com.fmss.hr.mapper.UserMapper;
import com.fmss.hr.services.user.impl.TimeSheetServiceImpl;
import com.fmss.hr.services.user.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserServiceImpl userService;
    private final TimeSheetServiceImpl timeSheetService;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> getByUserId(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        UserResponse userResponse = UserMapper.userToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("myCandidates/{userId}")
    public ResponseEntity<List<CandidateDto>> myCandidates(@PathVariable Long userId){
        List<CandidateDto> candidates = userService.myCandidates(userId);
        if(!candidates.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(candidates);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @RequestMapping(value ="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest loginRequest) {
        final LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> signUp(@PathParam(value = "Sign up Request")
                                    @Valid @RequestBody SignUpRequest signUpRequest) {
        Long check = userService.signUp(signUpRequest);
        if(check != null){
            timeSheetService.createTimeSheet(check);
            return ResponseEntity.status(HttpStatus.OK).body(true);        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestPart("updateRequest") UserRequest newUser,@RequestParam("file") MultipartFile file){
        if(!file.isEmpty()){
            try{
                User userToUpdate = userService.getUserById(userId);
                String photoPath = userService.userProfilePhotosSave(file, userToUpdate.getPhotoPath());
                User user = userService.updateUser(userId, newUser,photoPath);
                if(user != null){
                    return ResponseEntity.status(HttpStatus.OK).body(user);
                }
            }catch (Exception e){
                System.out.println("Hata");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        if(user != null){
            userService.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted!");        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find the user");
    }
    @GetMapping("/numberOfUsers")
    public ResponseEntity<Integer> numberOfUsers(){
        return ResponseEntity.ok(userService.numberOfUsers());
    }

    @GetMapping("/numberOfPassiveUsers")
    public ResponseEntity<Integer> numberOfPassiveUsers(){
        return ResponseEntity.ok(userService.numberOfPassiveUsers());
    }

    @GetMapping("/numberOfFilteredUsers")
    public ResponseEntity<Integer> numberOfFilteredUsers(@RequestParam(value="filter", required = false) String filter){
        return ResponseEntity.ok(userService.numberOfFilteredUsers(filter, filter, filter));
    }

    @GetMapping("/numberOfFilteredAndPassiveUsers")
    public ResponseEntity<Integer> numberOfFilteredAndPassiveUsers(@RequestParam(value="filter", required = false) String filter){
        return ResponseEntity.ok(userService.numberOfFilteredAndPassiveUsers(filter, filter, filter));
    }

    @GetMapping("/myCandidatesCount")
    public ResponseEntity<Integer> myCandidatesCount(@RequestParam Long userId){
        return ResponseEntity.ok(userService.myCandidatesCount(userId));
    }

    @PostMapping("/makeActive")
    public ResponseEntity<?> makeActive(@RequestParam Long userId){
        userService.makeActive(userId);
        return ResponseEntity.ok("Kullanıcı aktif hale getirildi");
    }

    @PostMapping("/makePassive")
    public ResponseEntity<?> makePassive(@RequestParam Long userId){
        userService.makePassive(userId);
        return ResponseEntity.ok("Kullanıcı pasif hale getirildi");
    }

    @GetMapping("getAllByStatusIsTrue/{index}")
    public ResponseEntity<List<UserResponse>> getAllByStatusIsTrue(@PathVariable int index, @RequestParam(value="filter", required = false) String filter){
        return ResponseEntity.ok(userService.getAllByStatusIsTrue(index, filter));
    }

    @GetMapping("getAllByStatusIsFalse/{index}")
    public ResponseEntity<List<UserResponse>> getAllByStatusIsFalse(@PathVariable int index, @RequestParam(value="filter", required = false) String filter){
        return ResponseEntity.ok(userService.getAllByStatusIsFalse(index, filter));
    }

    @GetMapping("getAllBirthdayEvent")
    public ResponseEntity<List<BirthdayEventDto>> getBirthdayEventDto(){
        return ResponseEntity.ok(userService.getBirthdayEventList());
    }

    @PostMapping(value ="/upload")
    @ApiOperation("Upload a photo")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            //userService.userProfilePhotosSave(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        catch (Exception e) {
            message = "Could not upload the file: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping(value ="/view/{fileName:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> getFile(@PathVariable String fileName) {
        Resource file = userService.getFileByName(fileName);

        if (file != null)
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping(value = "/getAllPageable/{index}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllPageable(@PathVariable int index) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllPageable(index));
    }

    @GetMapping(value = "/getAllUsersInfo/{index}")
    public ResponseEntity<List<UserInfoDto>> getAllUsersInfo(@PathVariable int index, @RequestParam(value="filter", required = false) String filter){
        List<UserInfoDto> userInfoDtoList = userService.getAllUsersInfo(index, filter);
        if(userInfoDtoList != null){
            return ResponseEntity.status(HttpStatus.OK).body(userInfoDtoList);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(value = "/changePassword/{userId}")
    public ResponseEntity<Boolean> changePassword(@PathVariable Long userId, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword){
        Boolean isSuccess = userService.changePassword(userId, oldPassword, newPassword);
        if (Boolean.TRUE.equals(isSuccess)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
}