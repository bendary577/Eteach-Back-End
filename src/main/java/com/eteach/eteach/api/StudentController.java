package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.StudentAccount;
import com.eteach.eteach.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/")
    public String postStudent(@Valid @RequestBody StudentAccount studentAccount) {
        this.accountService.createStudent(studentAccount);
        return "saved";
    }

    @PostMapping("/upload/{id}/image/")
    public String uploadImage(@PathVariable(value = "id") Long id, @Valid @NotNull @NotEmpty @RequestParam("image") MultipartFile image) throws IOException {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        studentAccount.setImage(fileName);
        String imageUploadDirectory = "user-photos/" + studentAccount.getId();
        FileUpload.saveFile(imageUploadDirectory, fileName, image);
        this.accountService.createStudent(studentAccount);
        return "saved";
    }

    @GetMapping("/")
    public List<StudentAccount> getAllStudents() {
        List<StudentAccount> studentAccounts;
        studentAccounts = accountService.getAllStudents();
        if(studentAccounts == null){
            throw new ResourceNotFoundException("Student", "id", 001);
        }
        return studentAccounts;
    }

    @GetMapping("/{id}")
    public StudentAccount getStudent(@PathVariable(value = "id") Long id) {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        return studentAccount;
    }

    @PutMapping("/{id}")
    public StudentAccount updateStudent(@PathVariable(value = "id") Long id, @Valid @RequestBody StudentAccount newStudentAccount) {
        StudentAccount oldStudentAccount = accountService.getStudent(id);
        if(oldStudentAccount == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        return accountService.updateStudent(oldStudentAccount, newStudentAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {
        StudentAccount studentAccount = accountService.getStudent(id);
        if(studentAccount == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        accountService.deleteStudent(studentAccount);
        return ResponseEntity.ok().build();
    }

}