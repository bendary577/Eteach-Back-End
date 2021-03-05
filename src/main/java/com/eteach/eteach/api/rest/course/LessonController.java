package com.eteach.eteach.api.rest.course;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Lesson;
import com.eteach.eteach.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/lessons", produces = "application/json;charset=UTF-8")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService){
        this.lessonService = lessonService;
    }

    //----------------------------- CREATE A NEW LESSON ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/")
    public String postLesson(@Valid @RequestBody Lesson lesson){
        this.lessonService.createLesson(lesson);
        return "saved";
    }

    //----------------------------- GET ALL LESSONS ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    //----------------------------- GET A SINGLE LESSON ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Lesson getLesson(@PathVariable(value = "id") Long id) {
        return lessonService.getLesson(id);
    }

    //----------------------------- UPDATE A SINGLE LESSON ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson newLesson) {
        Lesson oldLesson = lessonService.getLesson(id);
        if(oldLesson == null){
            throw new ResourceNotFoundException("lesson", "id", id);
        }
        return lessonService.updateLesson(oldLesson, newLesson);
    }

    //----------------------------- DELETE A SINGLE LESSON ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable(value = "id") Long id) {
        Lesson lesson = lessonService.getLesson(id);
        if(lesson == null){
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
        lessonService.deleteLesson(lesson);
        return ResponseEntity.ok().build();
    }

}