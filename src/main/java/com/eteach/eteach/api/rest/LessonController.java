package com.eteach.eteach.api.rest;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.course.Lesson;
import com.eteach.eteach.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/")
    public String postLesson(@Valid @RequestBody Lesson lesson){
        this.lessonService.createLesson(lesson);
        return "saved";
    }

    @GetMapping("/")
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public Lesson getLesson(@PathVariable(value = "id") Long id) {
        return lessonService.getLesson(id);
    }

    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson newLesson) {
        Lesson oldLesson = lessonService.getLesson(id);
        if(oldLesson == null){
            throw new ResourceNotFoundException("lesson", "id", id);
        }
        return lessonService.updateLesson(oldLesson, newLesson);
    }

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