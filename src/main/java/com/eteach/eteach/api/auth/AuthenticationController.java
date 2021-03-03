package com.eteach.eteach.api.auth;

import com.eteach.eteach.enums.AccountType;
import com.eteach.eteach.event.resetPasswordLink.GenerateResetLinkEvent;
import com.eteach.eteach.event.resetPasswordLink.GenerateResetLinkPublisher;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.account.User;
import com.eteach.eteach.jwt.JwtTokenProvider;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.JwtAuthenticationResponse;
import com.eteach.eteach.http.request.LoginRequest;
import com.eteach.eteach.http.request.SignUpRequest;
import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import com.eteach.eteach.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import static com.eteach.eteach.security.rolesandpermessions.Role.*;

@RestController
@RequestMapping(value="/api/v1/auth/", produces = "application/json;charset=UTF-8")
public class AuthenticationController {

    public final AuthenticationManager authenticationManager;
    public final PasswordEncoder passwordEncoder;
    public final JwtTokenProvider JwtTokenProvider;
    public final ApplicationUserService applicationUserService;
    public final RedisService redisService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                   PasswordEncoder passwordEncoder,
                                   JwtTokenProvider JwtTokenProvider,
                                   ApplicationUserService applicationUserService,
                                    RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.JwtTokenProvider = JwtTokenProvider;
        this.applicationUserService = applicationUserService;
        this.redisService = redisService;
    }

    /*-------------------------------- SIGNIN -------------------------------------------*/
    @PostMapping("/signin/")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

        //AUTHENTICATE THE USER
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        //SET USER AUTHENTICATION
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //GET USER DETAILS
        ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();

        //GENERATE A NEW AUTH TOKEN FOR THE NEW USER
        String jwtToken = JwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, applicationUser.getUsername(), applicationUser.getAuthorities()));
    }

    /*------------------------------------ AUTHENTICATE USER CREDENTIALS -------------------------------------*/
    private Authentication authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
            return authentication;
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("exception is in signup controller in bad credentials");
            throw new Exception("INVALID_CREDENTIALS FOR USER", e);
        }
    }

    /*--------------------------------SIGNUP-------------------------------------------*/
    @PostMapping("/signup/")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(applicationUserService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(HttpStatus.IM_USED, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(applicationUserService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(HttpStatus.IM_USED, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        //CREATE NEW USER INSTANCE
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             passwordEncoder.encode(signUpRequest.getPassword()),
                             signUpRequest.getPhone_number());


        //CREATE NEW PROFILE
        Account account = null;
        if(signUpRequest.getAccountType() == AccountType.STUDENT.getAccountCode()){
            account = new TeacherAccount();
            user.setRole(TEACHER);
        }else if(signUpRequest.getAccountType() == AccountType.TEACHER.getAccountCode()){
            account = new StudentAccount();
            user.setRole(STUDENT);
        }
        user.setAccount(account);

        //SAVE THE NEW USER AND RETURN IT'S DETAILS
        User result = applicationUserService.createUser(user);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "teacher" + user.getUsername() +"registered successfully"));
    }

    /*-------------------------------- SEND RESET PASSWORD LINK ------------------------------*/
    @PostMapping("/password/resetlink")
    public ResponseEntity<?> resetLink(@Valid @RequestBody String email) throws Exception {
        User user = applicationUserService.getUserByemail(email);
        if(user == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "user with this email is not found"));
        }
        //DELETE THE CURRENT TOKEN ASSIGNED WITH USER
        redisService.deleteValue(user.getUsername());
        //GENERATE NEW TOKEN
        Authentication authentication = authenticate(user.getUsername(), user.getPassword());
        String newJwtToken = JwtTokenProvider.generateToken(authentication);
        //PUBLISH GENERATE RESET LINK EVENT AND SEND MAIL
        //GenerateResetLinkEvent generateResetLinkEvent = new GenerateResetLinkEvent(urlBuilder);
        //GenerateResetLinkPublisher generateResetLinkPublisher = new GenerateResetLinkPublisher();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Password reset link sent successfully"));
    }

    /*------------------------------ REFRESH TOKEN ---------------------------------------*/

    @GetMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        ApplicationUser user = (ApplicationUser) request.getAttribute("user");
        String refreshedToken = JwtTokenProvider.generateRefreshToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, user.getUsername(), user.getAuthorities()));
    }





}
