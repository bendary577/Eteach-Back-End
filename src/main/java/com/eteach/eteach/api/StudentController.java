package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Student;
import com.eteach.eteach.service.StudentService;
import com.eteach.eteach.utils.FileUpload;
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

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/")
    public String postStudent(@Valid @RequestBody Student student) {
        this.studentService.createStudent(student);
        return "saved";
    }

    @PostMapping("/upload/{id}/image/")
    public String uploadImage(@PathVariable(value = "id") Long id, @Valid @NotNull @NotEmpty @RequestParam("image") MultipartFile image) throws IOException {
        Student student = studentService.getStudent(id);
        if(student == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        student.setImage(fileName);
        String imageUploadDirectory = "user-photos/" + student.getId();
        FileUpload.saveFile(imageUploadDirectory, fileName, image);
        this.studentService.createStudent(student);
        return "saved";
    }

    @GetMapping("/")
    public List<Student> getAllStudents() {
        List<Student> students;
        students = studentService.getAllStudents();
        if(students == null){
            throw new ResourceNotFoundException("Student", "id", 001);
        }
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable(value = "id") Long id) {
        Student student = studentService.getStudent(id);
        if(student == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        return student;
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