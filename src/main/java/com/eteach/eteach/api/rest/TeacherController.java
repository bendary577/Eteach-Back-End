package com.eteach.eteach.api.rest;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/teachers", produces = "application/json;charset=UTF-8")
public class TeacherController {

    private final AccountService accountService;

    @Autowired
    public TeacherController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/")
    public String postTeacher(@Valid @RequestBody TeacherAccount teacherAccount){
        this.accountService.createTeacher(teacherAccount);
        return "saved";
    }

    @GetMapping("/")
    public List<TeacherAccount> getAllTeachers() {
        return accountService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherAccount getTeacher(@PathVariable(value = "id") Long id) {
        return accountService.getTeacher(id);
    }

    @PutMapping("/{id}")
    public TeacherAccount updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody TeacherAccount newTeacherAccount) {
        TeacherAccount oldTeacherAccount = accountService.getTeacher(id);
        if(oldTeacherAccount == null){
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
        return accountService.updateTeacher(oldTeacherAccount, newTeacherAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value = "id") Long id) {
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        if(teacherAccount == null){
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
        accountService.deleteTeacher(teacherAccount);
        return ResponseEntity.ok().build();
    }

}