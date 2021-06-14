package com.eteach.eteach.api.rest.accounts;

import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.http.request.authRequest.UpdatePasswordRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.profileResponse.AdminProfileResponse;
import com.eteach.eteach.http.response.profileResponse.StudentProfileResponse;
import com.eteach.eteach.http.response.profileResponse.TeacherProfileResponse;
import com.eteach.eteach.jwt.JwtTokenProvider;
import com.eteach.eteach.model.account.*;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import com.eteach.eteach.utils.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value ="/api/v1/user", produces = "application/json;charset=UTF-8")
public class UserController {

    private final ApplicationUserService userService;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileStorageUtil fileStorageUtil;

    @Autowired
    public UserController(ApplicationUserService userService,
                          RedisService redisService,
                          JwtTokenProvider jwtTokenProvider,
                          FileStorageUtil fileStorageUtil){
        this.userService = userService;
        this.redisService = redisService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.fileStorageUtil = fileStorageUtil;
    }

    //---------------- RETURNS THE CURRENT USER PROFILE OF THE LOGGED IN USER ----------------
    @GetMapping("/me/")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN','ADMINTRAINEE')")
    public ResponseEntity getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String username = authentication.getName();
            User currentUser = userService.getUserByUsername(username);
            System.out.println("current username is " + currentUser.getUsername());
            Account account = currentUser.getAccount();
            //case user is teacher
            if(account instanceof TeacherAccount){
                TeacherProfileResponse response = prepareTeacherAccount(username, account);
                return ResponseEntity.ok(response);
            }else if(account instanceof StudentAccount){
                //case user is student
               StudentProfileResponse response = prepareStudentProfile(currentUser.getId(), username, account);
                return ResponseEntity.ok(response);
            }else if(account instanceof AdminAccount){
                AdminProfileResponse response = prepareAdminProfile(currentUser.getId(), username, account);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"user " + currentUser.getUsername() + "is logged in"));
        }

        return ResponseEntity.ok(new ApiResponse(HttpStatus.UNAUTHORIZED,"please login"));
    }

    //--------------------------- LOGOUT THE CURRENT USER ------------------------------------
    @PostMapping("/logout/")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN','ADMINTRAINEE')")
    public ResponseEntity logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String token = jwtTokenProvider.getJwtFromRequest(request);
        if(token != null) {
            redisService.saveValue(username, token);
        }else{
            return ResponseEntity.ok(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,"can't logout"));
        }
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"logged out successfully"));
    }

    //--------------------------- REFRESH TOKEN ------------------------------------

    @PostMapping("/password/update/")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN','ADMINTRAINEE')")
    public ResponseEntity updatePassword(ApplicationUser applicationUser, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String username = applicationUser.getUsername();
        User currentUser = userService.getUserByUsername(username);
        if(currentUser == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST,"current user is not found"));
        }
        userService.updateUserPassword(currentUser, updatePasswordRequest);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"updated password successfully"));
    }

    //--------------------------------- UPDATE ACCOUNT INFO ---------------------------------





    //--------------------------------- TEACHER PROFILE INFO ------------------------------------
    public TeacherProfileResponse prepareTeacherAccount(String username, Account account){
        Long id = account.getId();
        String accountType = "Teacher";
        String about = account.getAbout_description();
        byte[] image = null;
        Image imageFile = account.getImage();
        if(imageFile != null){
            try {
                image  = this.fileStorageUtil.readImageFromPath(imageFile);
            }catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
        Category subject = ((TeacherAccount) account).getSubject();
        String facebook_link = "";
        String twitter_link = "" ;

        if(((TeacherAccount) account).getFacebook_link() != null) facebook_link = ((TeacherAccount) account).getFacebook_link();
        if(((TeacherAccount) account).getTwitter_link() != null) twitter_link = ((TeacherAccount) account).getTwitter_link();

        return new TeacherProfileResponse(HttpStatus.OK, "teacher account returned successfully",id, username,
                about, image, accountType,facebook_link,
                twitter_link, subject);
    }

    //--------------------------------- STUDENT PROFILE INFO ------------------------------------
    public StudentProfileResponse prepareStudentProfile(Long id, String username, Account account){
        String accountType = "Student";
        byte[] image = null;
        String about = account.getAbout_description();
        Image imageFile = account.getImage();
        if(imageFile != null){
            try {
                image  = this.fileStorageUtil.readImageFromPath(imageFile);
            }catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
        Grade grade = ((StudentAccount) account).getGrade();
        String studentGrade = "";
        String address = ((StudentAccount) account).getAddress();

        //--------- handle nulls
        if(grade != null){
            studentGrade = grade.toString();
        }
        if(address == null) address = "";
        if(about == null) about = "";

        //---------- return responses
        return new StudentProfileResponse(HttpStatus.OK, "student account returned successfully", id, username,
                about, image, accountType,studentGrade, address);
    }

    //--------------------------------- ADMIN PROFILE INFO ------------------------------------
    public AdminProfileResponse prepareAdminProfile(Long id, String username, Account account){
        String accountType = "Admin";
        String about = account.getAbout_description();
       // String imagePath = account.getImagePath();
        byte[] image = null;
        return new AdminProfileResponse(HttpStatus.OK, "admin account returned successfully",id, username,
                about, image, accountType);
    }

}
