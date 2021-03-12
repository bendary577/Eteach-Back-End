package com.eteach.eteach.api.auth;

import com.eteach.eteach.enums.AccountType;
import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.http.request.signupRequest.StudentSignupRequest;
import com.eteach.eteach.http.request.signupRequest.TeacherSignupRequest;
import com.eteach.eteach.model.account.*;
import com.eteach.eteach.jwt.JwtTokenProvider;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.JwtAuthenticationResponse;
import com.eteach.eteach.http.request.LoginRequest;
import com.eteach.eteach.http.request.signupRequest.SignUpRequest;
import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUser;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import com.eteach.eteach.service.AccountService;
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
@RequestMapping(value="/api/v1/auth/",
                produces = "application/json;charset=UTF-8",
                headers = "Accept=*/*")
public class AuthenticationController {

    public final AuthenticationManager authenticationManager;
    public final PasswordEncoder passwordEncoder;
    public final JwtTokenProvider JwtTokenProvider;
    public final ApplicationUserService applicationUserService;
    public final RedisService redisService;
    private final AccountService accountService;
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                   PasswordEncoder passwordEncoder,
                                   JwtTokenProvider JwtTokenProvider,
                                   ApplicationUserService applicationUserService,
                                    RedisService redisService,
                                    AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.JwtTokenProvider = JwtTokenProvider;
        this.applicationUserService = applicationUserService;
        this.redisService = redisService;
        this.accountService = accountService;
    }

    /*-------------------------------- SIGNIN -------------------------------------------*/
    @CrossOrigin
    @PostMapping("/signin/")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

        //AUTHENTICATE THE USER
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if(authentication != null){
            //SET USER AUTHENTICATION
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //GET USER DETAILS
            ApplicationUser applicationUser = (ApplicationUser) authentication.getPrincipal();

            //GENERATE A NEW AUTH TOKEN FOR THE NEW USER
            String jwtToken = JwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, applicationUser.getUsername(), applicationUser.getAuthorities(), HttpStatus.OK, "logged in successfully "));
        }else{
            return ResponseEntity.ok(new ApiResponse(HttpStatus.UNAUTHORIZED, "username or password are invalid"));
        }
    }

    /*------------------------------------ AUTHENTICATE USER CREDENTIALS -------------------------------------*/
    private Authentication authenticate(String username, String password) throws Exception {
        try {
             User user = applicationUserService.getUserByUsername(username);
             if ( user == null){
                 System.out.println("username isn't registered");
                return null;
             }else {
                 System.out.println("username is registered");
                 System.out.println("user email :" + user.getEmail());
                 System.out.println("submitted password1 is " + password);
                 System.out.println("-----------------------------------------");
                 System.out.println("password1 is " + user.getPassword());
                 System.out.println("password2 is " + password);
                 if(!passwordEncoder.matches(password, user.getPassword())){
                     System.out.println("password is invalid");
                     return null;
                 }
                 System.out.println("username and password are valid");
                 Authentication authentication = authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(
                                 username,
                                 password
                         )
                 );
                 return authentication;
             }
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
        System.out.println("i'm in signup method");
        System.out.println("request username :" + signUpRequest.getUsername());
        System.out.println("request email :" + signUpRequest.getEmail());
        System.out.println("request password :" + signUpRequest.getPassword());
        System.out.println("request phone :" + signUpRequest.getPhone_number());
        System.out.println("request account type :" + signUpRequest.getAccountType());

        User user1 = applicationUserService.getUserByUsername(signUpRequest.getUsername());
        System.out.println("after user 1");
        User user2 = applicationUserService.getUserByEmail(signUpRequest.getEmail());
        System.out.println("user 1 is :" + user1);
        System.out.println("user 2 is :" + user2);

        if(user1 != null) {
            System.out.println("i'm in user1 ");
            return new ResponseEntity(new ApiResponse(HttpStatus.IM_USED, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(user2 != null){
            System.out.println("i'm in user2 ");
            return new ResponseEntity(new ApiResponse(HttpStatus.IM_USED, "email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        //CREATE NEW USER INSTANCE
        System.out.println("i'm creating user ");
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             passwordEncoder.encode(signUpRequest.getPassword()),
                             signUpRequest.getPhone_number());

        System.out.println("i created user method");

        if(signUpRequest instanceof TeacherSignupRequest){
            System.out.println("request is teacher");
            TeacherAccount account = new TeacherAccount();
            account.setSubject(((TeacherSignupRequest) signUpRequest).getSubject());
            account.setFacebook_link(((TeacherSignupRequest) signUpRequest).getFacebook_link());
            account.setTwitter_link(((TeacherSignupRequest) signUpRequest).getTwitter_link());
            user.setRole(TEACHER);
            user.setAccount(account);
            account.setUser(user);
            accountService.saveTeacher(account);
        }else if(signUpRequest instanceof StudentSignupRequest){
            System.out.println("request is student");
            StudentAccount account = new StudentAccount();
            account.setAddress(((StudentSignupRequest) signUpRequest).getAddress());
            Grade studentGrade = null;
            for (Grade grade : Grade.values()) {
                if(grade.toString().equals(((StudentSignupRequest) signUpRequest).getGrade())){
                    studentGrade = grade;
                }
            }
            account.setGrade(studentGrade);
            user.setRole(STUDENT);
            user.setAccount(account);
            account.setUser(user);
            accountService.saveStudent(account);
        } else {
            System.out.println("request is admin");
           AdminAccount account = new AdminAccount();
           user.setRole(ADMIN);
        }

        System.out.println("last signup");
        //SAVE THE NEW USER AND RETURN IT'S DETAILS
        User result = applicationUserService.createUser(user);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "user " + user.getUsername() +"registered successfully"));
    }

    /*-------------------------------- SEND RESET PASSWORD LINK ------------------------------*/
    @PostMapping("/password/resetlink")
    public ResponseEntity<?> resetLink(@Valid @RequestBody String email) throws Exception {
        User user = applicationUserService.getUserByEmail(email);
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
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        ApplicationUser user = (ApplicationUser) request.getAttribute("user");
        String refreshedToken = JwtTokenProvider.generateRefreshToken(user);
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, user.getUsername(), user.getAuthorities(), HttpStatus.OK, "token refreshed successfully"));
    }





}
