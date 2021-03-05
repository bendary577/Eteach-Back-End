package com.eteach.eteach.api.rest;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
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
@RequestMapping(value ="/api/v1/teachers", produces = "application/json;charset=UTF-8")
public class TeacherController {

    private final AccountService accountService;

    @Autowired
    public TeacherController(AccountService accountService){
        this.accountService = accountService;
    }

    //------------------------- CREATE A NEW TEACHER -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @PostMapping("/")
    public String postTeacher(@Valid @RequestBody TeacherAccount teacherAccount){
        this.accountService.saveTeacher(teacherAccount);
        return "saved";
    }

    //---------------------------- UPLOAD TEACHER IMAGE PROFILE --------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/upload/{id}/image/")
    public String uploadImage(@PathVariable(value = "id") Long id, @Valid @NotNull @NotEmpty @RequestParam("image") MultipartFile image) throws IOException {
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        if(teacherAccount == null){
            throw new ResourceNotFoundException("StudentAccount", "id", id);
        }
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        //studentAccount.setImage(fileName);
        String imageUploadDirectory = "user-photos/" + teacherAccount.getId();
        // FileUpload.saveFile(imageUploadDirectory, fileName, image);
        this.accountService.saveTeacher(teacherAccount);
        return "saved";
    }

    //------------------------- CREATE ALL TEACHERS WITH PAGINATION -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @GetMapping("/")
    public List<TeacherAccount> getAllTeachers(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        List<TeacherAccount> teacherAccounts;
        teacherAccounts = accountService.getAllTeachers(pageNo, pageSize);
        if(teacherAccounts == null){
            throw new ResourceNotFoundException("TeacherAccount", "id", 001);
        }
        return teacherAccounts;
    }

    //------------------------- GET A SINGLE TEACHER -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_TEACHER','ROLE_STUDENT')")
    @GetMapping("/{id}")
    public TeacherAccount getTeacher(@PathVariable(value = "id") Long id) {
        return accountService.getTeacher(id);
    }


    //------------------------ UPDATE A TEACHER ACCOUNT ----------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_TEACHER')")
    @PutMapping("/{id}")
    public TeacherAccount updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody TeacherAccount newTeacherAccount) {
        TeacherAccount oldTeacherAccount = accountService.getTeacher(id);
        if(oldTeacherAccount == null){
            throw new ResourceNotFoundException("TeacherAccount", "id", id);
        }
        return accountService.updateTeacher(oldTeacherAccount, newTeacherAccount);
    }

    //------------------------- DELETE A SINGLE TEACHER -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value = "id") Long id) {
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        if(teacherAccount == null){
            throw new ResourceNotFoundException("TeacherAccount", "id", id);
        }
        accountService.deleteTeacher(teacherAccount);
        return ResponseEntity.ok().build();
    }

}