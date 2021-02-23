package com.eteach.eteach.api;

import com.eteach.eteach.enums.AccountType;
import com.eteach.eteach.model.StudentAccount;
import com.eteach.eteach.model.TeacherAccount;
import com.eteach.eteach.model.User;
import com.eteach.eteach.jwt.JwtTokenProvider;
import com.eteach.eteach.http.ApiResponse;
import com.eteach.eteach.http.JwtAuthenticationResponse;
import com.eteach.eteach.http.LoginRequest;
import com.eteach.eteach.http.SignUpRequest;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import com.eteach.eteach.model.Account;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import static com.eteach.eteach.security.rolesandpermessions.Role.*;

@RestController
@RequestMapping(value="/api/v1/auth/", produces = "application/json;charset=UTF-8")
public class AuthenticationController {

    public final AuthenticationManager authenticationManager;
    public final PasswordEncoder passwordEncoder;
    public final JwtTokenProvider JwtTokenProvider;
    public final ApplicationUserService applicationUserService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                   PasswordEncoder passwordEncoder,
                                   JwtTokenProvider JwtTokenProvider,
                                   ApplicationUserService applicationUserService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.JwtTokenProvider = JwtTokenProvider;
        this.applicationUserService = applicationUserService;
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
        if(signUpRequest.getAccountType().equals(AccountType.STUDENT)){
            account = new TeacherAccount();
            user.setRole(TEACHER);
        }else if(signUpRequest.getAccountType().equals(AccountType.TEACHER)){
            account = new StudentAccount();
            user.setRole(STUDENT);
        }
        user.setAccount(account);

        //SAVE THE NEW USER AND RETURN IT'S DETAILS
        User result = applicationUserService.createUser(user);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "teacher" + user.getUsername() +"registered successfully"));
    }



}
