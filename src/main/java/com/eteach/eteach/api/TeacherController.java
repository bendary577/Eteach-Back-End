package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Teacher;
import com.eteach.eteach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private final TeacherService teacherService;

    @Value("${server.compression.mime-types}")
    private List<String> contentVideos;

    @Autowired
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @PostMapping("/")
    public String postTeacher(@Valid @RequestBody Teacher teacher){
        this.teacherService.createTeacher(teacher);
        return "saved";
    }

    @PostMapping(value= "/upload/{id}/video/", consumes = {
                                                    MediaType.MULTIPART_FORM_DATA_VALUE,
                                                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public String uploadVideo( @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile video)throws IOException {
        String contentType = video.getContentType();
        if (!contentVideos.contains(contentType)) {
            return "error";
        }

            return "saved";
    }

    @GetMapping("/")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable(value = "id") Long id) {
        return teacherService.getTeacher(id);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable(value = "id") Long id, @Valid @RequestBody Teacher newTeacher) {
        Teacher oldTeacher = teacherService.getTeacher(id);
        if(oldTeacher == null){
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
        return teacherService.updateTeacher(oldTeacher, newTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(value = "id") Long id) {
        Teacher teacher = teacherService.getTeacher(id);
        if(teacher == null){
            throw new ResourceNotFoundException("Teacher", "id", id);
        }
        teacherService.deleteTeacher(teacher);
        return ResponseEntity.ok().build();
    }

}