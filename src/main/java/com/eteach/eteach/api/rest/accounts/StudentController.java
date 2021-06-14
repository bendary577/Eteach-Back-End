package com.eteach.eteach.api.rest.accounts;

import com.eteach.eteach.dao.FileDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.student.StudentResponse;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.service.AccountService;
import com.eteach.eteach.service.FileService;
import com.eteach.eteach.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/students", produces = "application/json;charset=UTF-8")
public class StudentController {

    private final AccountService accountService;
    private final FileService fileService;

    @Autowired
    public StudentController(AccountService accountService,
                             FileService fileService){
        this.accountService = accountService;
        this.fileService = fileService;
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
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error, we can't process image"));
        }
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error, we can't find the requested account"));
        }
        String contentType = profileImage.getContentType();
        Long size = profileImage.getSize();
        if (!fileService.validateImageFile(contentType, size)) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "profile image is not valid"));
        }
        Path path = Paths.get(String.valueOf(this.getClass().getResource(File.separator + "resources"+ File.separator + "data" + File.separator + "accounts" + File.separator + studentAccount.getId().toString())));
        String absolutePath = path.toFile().getAbsolutePath();
        Image image = fileService.createImageFile(profileImage, path);
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
    @GetMapping("/{id}/")
    public ResponseEntity<?> getStudent(@PathVariable(value = "id") Long id) {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "sorry, we can't find desired student account"));
        }
        return ResponseEntity.ok(new StudentResponse(HttpStatus.OK, "student account returned successfully",studentAccount));
    }

    //----------------------------- UPDATE STUDENT INFO -------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE', 'ROLE_STUDENT')")
    @PutMapping("/{id}/")
    public StudentAccount updateStudent(@PathVariable(value = "id") Long id, @Valid @RequestBody StudentAccount newStudentAccount) {
        StudentAccount oldStudentAccount = accountService.getStudent(id);
        if(oldStudentAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        return accountService.updateStudent(oldStudentAccount, newStudentAccount);
    }

    //----------------------------- DELETE A SINGLE STUDENT ACCOUNT --------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        accountService.deleteStudent(studentAccount);
        return ResponseEntity.ok().build();
    }


}