package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Course;
import com.eteach.eteach.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/courses", produces = "application/json;charset=UTF-8")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/")
    public String postCourse(@Valid @RequestBody Course course){
        this.courseService.createCourse(course);
        return "saved";
    }

    @GetMapping("/")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable(value = "id") Long id) {
        return courseService.getCourse(id);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable(value = "id") Long id, @Valid @RequestBody Course newCourse) {
        Course oldCourse = courseService.getCourse(id);
        if(oldCourse == null){
            throw new ResourceNotFoundException("Course", "id", id);
        }
        return courseService.updateCourse(oldCourse, newCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long id) {
        Course course = courseService.getCourse(id);
        if(course == null){
            throw new ResourceNotFoundException("Course", "id", id);
        }
        courseService.deleteCourse(course);
        return ResponseEntity.ok().build();
    }

}