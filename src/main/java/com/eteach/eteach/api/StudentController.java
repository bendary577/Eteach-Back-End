package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Student;
import com.eteach.eteach.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/students", produces = "application/json;charset=UTF-8")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/")
    public String postStudent(@Valid @RequestBody Student student){
        this.studentService.createStudent(student);
        return "saved";
    }

    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable(value = "id") Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable(value = "id") Long id, @Valid @RequestBody Student newStudent) {
        Student oldStudent = studentService.getStudent(id);
        if(oldStudent == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        return studentService.updateStudent(oldStudent, newStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {
        Student student = studentService.getStudent(id);
        if(student == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        studentService.deleteStudent(student);
        return ResponseEntity.ok().build();
    }

}