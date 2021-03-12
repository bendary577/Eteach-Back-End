package com.eteach.eteach.api.rest.accounts;

import com.eteach.eteach.config.file.UserDataConfig;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.file.File;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import com.eteach.eteach.model.quiz.Quiz;
import com.eteach.eteach.service.AccountService;
import com.eteach.eteach.service.FileService;
import com.eteach.eteach.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value ="/api/v1/students", produces = "application/json;charset=UTF-8")
public class StudentController {

    private final QuizService quizService;
    private final AccountService accountService;
    private final FileService fileService;
    private final ServletContext context;

    @Autowired
    public StudentController(AccountService accountService,
                             QuizService quizService,
                             FileService fileService,
                             ServletContext context){
        this.accountService = accountService;
        this.quizService = quizService;
        this.fileService = fileService;
        this.context = context;
    }

    //-------------------------- CREATE A NEW STUDENT ------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PostMapping("/")
    public String postStudent(@Valid @RequestBody StudentAccount studentAccount) {
        this.accountService.saveStudent(studentAccount);
        return "saved";
    }

    //---------------------------- UPLOAD STUDENT IMAGE PROFILE --------------------------------------
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping(value = "/upload/{id}/image/",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @Valid @NotNull @NotEmpty @RequestPart("user_photo") MultipartFile profileImage) throws IOException {
        if(profileImage == null){
            System.out.println("image is null");
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error, we can't process image"));
        }
        StudentAccount studentAccount = accountService.getStudent(id);
        System.out.println("i'm in upload profile image rest api");
        if(studentAccount == null){
            System.out.println("student account is null");
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error, we can't find the requested account"));
        }
        String contentType = profileImage.getContentType();
        Long size = profileImage.getSize();
        if (!fileService.validateImageFile(contentType, size)) {
            System.out.println("profile image validation failed");
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "profile image is not valid"));
        }
        Path path = Paths.get("src","main","resources", "data", "accounts", studentAccount.getId().toString());
        String absolutePath = path.toFile().getAbsolutePath();
        System.out.println("student image path is : " + path);
            System.out.println("student image absolute path is : " + absolutePath);
        Image image = fileService.createImageFile(profileImage, path);
        System.out.println("image created successfully");
        studentAccount.setImage(image);
        this.accountService.saveStudent(studentAccount);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "profile image uploaded successfully TO PATH : " + studentAccount.getImage().getPath() + " with name" + studentAccount.getImage().getName() ));
    }

    //----------------------------- GET ALL STUDENT ACCOUNTS WITH PAGINATION -----------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @GetMapping("/")
    public List<StudentAccount> getAllStudents(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        List<StudentAccount> studentAccounts;
        studentAccounts = accountService.getAllStudents(pageNo, pageSize);
        if(studentAccounts == null){
            throw new ResourceNotFoundException("StudentAccount", "id", 001);
        }
        return studentAccounts;
    }

    //----------------------------- GET A SINGLE STUDENT ACCOUNT ---------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_TEACHER','ROLE_STUDENT')")
    @GetMapping("/{id}")
    public StudentAccount getStudent(@PathVariable(value = "id") Long id) {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        return studentAccount;
    }

    //----------------------------- UPDATE STUDENT INFO -------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE', 'ROLE_STUDENT')")
    @PutMapping("/{id}")
    public StudentAccount updateStudent(@PathVariable(value = "id") Long id, @Valid @RequestBody StudentAccount newStudentAccount) {
        StudentAccount oldStudentAccount = accountService.getStudent(id);
        if(oldStudentAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        return accountService.updateStudent(oldStudentAccount, newStudentAccount);
    }

    //----------------------------- DELETE A SINGLE STUDENT ACCOUNT --------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        accountService.deleteStudent(studentAccount);
        return ResponseEntity.ok().build();
    }

    //------------------------------ ASSIGN QUIZ TO STUDENT ------------------------------------------------
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/{quizId}")
    public ResponseEntity<?> takeQuiz(@PathVariable(value = "quizId") Long quizId, @Valid @RequestBody Long studentId) {
        //get the quiz and the student account
        Quiz quiz = quizService.getQuiz(quizId);
        StudentAccount studentAccount = accountService.getStudent(studentId);

        //create student_quiz_key
        StudentQuizKey studentQuizKey = new StudentQuizKey();
        studentQuizKey.setQuizId(quizId);
        studentQuizKey.setStudentId(studentId);

        //create student_quiz
        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(studentQuizKey);
        studentQuiz.setStudent(studentAccount);
        studentQuiz.setQuiz(quiz);

        //assign quiz to student
        studentAccount.getQuizzes().add(studentQuiz);
        quiz.getStudents().add(studentQuiz);

        //save info in database
        accountService.saveStudent(studentAccount);
        quizService.saveQuiz(quiz);

        return ResponseEntity.ok().build();
    }

    //--------------- UPLOAD QUIZ ANSWERS : THEN MARK THE QUIZ AND RETURN MARK ---------------------------//
    //takes quizId, student choices array, then return the student mark



}