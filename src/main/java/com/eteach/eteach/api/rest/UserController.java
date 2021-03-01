package com.eteach.eteach.api.rest;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import com.eteach.eteach.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity getUserProfile(ApplicationUser currentUser) {
        //logger.info(currentUser.getUser().getEmail() + " has role: " + currentUser.getUser().getRole());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"Hello" +currentUser.getUser().getUsername()+"to your profile"));
    }

    //--------------------------- LOGOUT THE CURRENT USER ------------------------------------
    @GetMapping("/logout")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER','ADMIN','ADMINTRAINEE')")
    public ResponseEntity logout() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisService.deleteValue(username);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"logged out successfully"));
    }

    //--------------------------- LOGOUT THE CURRENT USER ------------------------------------



















}
