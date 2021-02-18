package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.TeacherAccount;
import com.eteach.eteach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/teachers", produces = "application/json;charset=UTF-8")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("/")
    public String postTeacher(@Valid @RequestBody TeacherAccount teacherAccount){
        this.teacherService.createTeacher(teacherAccount);
        return "saved";
    }

    @GetMapping("/")
    public List<TeacherAccount> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherAccount getTeacher(@PathVariable(value = "id") Long id) {
        return teacherService.getTeacher(id);
    }

    @PutMapping("/{id}")
    public TeacherAccount updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody TeacherAccount newTeacherAccount) {
        TeacherAccount oldTeacherAccount = teacherService.getTeacher(id);
        if(oldTeacherAccount == null){
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
        return teacherService.updateTeacher(oldTeacherAccount, newTeacherAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value = "id") Long id) {
        TeacherAccount teacherAccount = teacherService.getTeacher(id);
        if(teacherAccount == null){
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
        teacherService.deleteTeacher(teacherAccount);
        return ResponseEntity.ok().build();
    }

}