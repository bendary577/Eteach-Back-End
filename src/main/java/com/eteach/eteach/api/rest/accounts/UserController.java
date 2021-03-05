package com.eteach.eteach.api.rest.accounts;

import com.eteach.eteach.http.request.UpdatePasswordRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.account.User;
import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/api/v1/user", produces = "application/json;charset=UTF-8")
public class UserController {

    private final ApplicationUserService userService;
    private final RedisService redisService;

    @Autowired
    public UserController(ApplicationUserService userService, RedisService redisService){
        this.userService = userService;
        this.redisService = redisService;
    }

    //---------------- RETURNS THE CURRENT USER PROFILE OF THE LOGGED IN USER ----------------
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN','ADMINTRAINEE')")
    public User getUserProfile(ApplicationUser applicationUser) {
        String username = applicationUser.getUsername();
        User currentUser = userService.getUserByUsername(username);
        return currentUser;
    }

    //--------------------------- LOGOUT THE CURRENT USER ------------------------------------
    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN','ADMINTRAINEE')")
    public ResponseEntity logout() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisService.deleteValue(username);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"logged out successfully"));
    }

    //--------------------------- REFRESH TOKEN ------------------------------------

    @PostMapping("/password/update")
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


}
