package com.eteach.eteach.api;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Course;
import com.eteach.eteach.service.CourseService;
import com.eteach.eteach.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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
@RequestMapping(value ="/api/v1/courses", produces = "application/json;charset=UTF-8")
public class CourseController {

    private final CourseService courseService;

    /*@Value("${server.compression.mime-types}")
    private List<String> contentVideos;
    */

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/")
    public String postCourse(@Valid @RequestBody Course course){
        this.courseService.createCourse(course);
        return "saved";
    }

    /*
    @PostMapping(value= "/upload/{id}/trailer_video/", consumes = {
                                                    MediaType.MULTIPART_FORM_DATA_VALUE,
                                                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public String uploadTrailerVideo(@PathVariable(value = "id") Long id, @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile video)throws IOException {
        Course course = courseService.getCourse(id);
        String contentType = video.getContentType();
        final long limit = 200 * 1024 * 1024;
        if(course == null){
            throw new ResourceNotFoundException("Student", "id", id);
        }
        if (!contentVideos.contains(contentType) || video.getSize() > limit ) {
            return "error";
        }
        String fileName = StringUtils.cleanPath(video.getOriginalFilename());
        course.setImage(fileName);
        String trailerVideoUploadDirectory = "videos/" + course.getId();
        FileUpload.saveFile(trailerVideoUploadDirectory, fileName, video);
        this.courseService.createCourse(course);
        return "saved";
    }
    */

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