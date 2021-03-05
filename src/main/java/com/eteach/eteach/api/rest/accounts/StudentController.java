package com.eteach.eteach.api.rest.accounts;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/students", produces = "application/json;charset=UTF-8")
public class StudentController {

    private final AccountService accountService;

    @Autowired
    public StudentController(AccountService accountService){
        this.accountService = accountService;
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
    @PostMapping("/upload/{id}/image/")
    public String uploadImage(@PathVariable(value = "id") Long id, @Valid @NotNull @NotEmpty @RequestParam("image") MultipartFile image) throws IOException {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        //studentAccount.setImage(fileName);
        String imageUploadDirectory = "user-photos/" + studentAccount.getId();
       // FileUpload.saveFile(imageUploadDirectory, fileName, image);
        this.accountService.saveStudent(studentAccount);
        return "saved";
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

    //--------------- UPLOAD QUIZ ANSWERS : THEN MARK THE QUIZ AND RETURN MARK ---------------------------//




}