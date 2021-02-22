package com.eteach.eteach.api;
import com.eteach.eteach.enums.FileTypes;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.ApiResponse;
import com.eteach.eteach.model.Course;
import com.eteach.eteach.model.File;
import com.eteach.eteach.service.CourseService;
import com.eteach.eteach.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value ="/api/v1/courses", produces = "application/json;charset=UTF-8")
public class CourseController {

    private final CourseService courseService;
    private final FileService fileService;

    @Autowired
    public CourseController(CourseService courseService, FileService fileService){
        this.courseService = courseService;
        this.fileService = fileService;
    }

    /*------------------------------------ SAVE A NEW COURSE ------------------------------------- */
    @PostMapping("/")
    public ResponseEntity<?> postCourse(@Valid @RequestBody Course course){
        this.courseService.createCourse(course);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"course saved successfully"));
    }


    /*------------------------------------ UPLOAD COURSE TRAILER VIDEO ------------------------------------- */
    @PostMapping(value= "/upload/{id}/video/", consumes = {
                                                    MediaType.MULTIPART_FORM_DATA_VALUE,
                                                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseVideo(@PathVariable(value = "id") Long id,
                                                @PathVariable(value = "type") String type,
                                                @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile file)throws IOException {
        Course course = courseService.getCourse(id);
        String contentType = file.getContentType();
        Long size = file.getSize();

        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST,"course not found"));
        }
        if(!fileService.validateVideoFile(contentType, size)){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST,"trailer video is not valid"));
        }
        File trailer_video = uploadFile(type, file, course);
        course.setTrailer_video(trailer_video);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"trailer video uploaded successfully"));
    }

    /*------------------------------------ UPLOAD COURSE THUMBNAIL ------------------------------------- */
    @PostMapping(value= "/upload/{id}/thumbnail/", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseThumbnail(@PathVariable(value = "id") Long id,
                                          @PathVariable(value = "type") String type,
                                          @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile file)throws IOException {
        Course course = courseService.getCourse(id);
        String contentType = file.getContentType();
        Long size = file.getSize();

        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST,"course not found"));
        }
        if(!fileService.validateImageFile(contentType, size)){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST,"thumbnail is not valid"));
        }
        File thumbnail = uploadFile(type, file, course);
        course.setThumbnail(thumbnail);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"course thumbnail uploaded successfully"));
    }

    /*------------------------------------ GET ALL COURSES WITH PAGINATION ------------------------------ */
    @GetMapping("/")
    public List<Course> getAllCourses( @RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return courseService.getAllCourses(pageNo, pageSize);
    }

    /*------------------------------------ GET A SINGLE COURSE ----------------------------------------- */
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable(value = "id") Long id) {
        return courseService.getCourse(id);
    }

    /*------------------------------------ UPDATE COURSE INFO ------------------------------------- */
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable(value = "id") Long id,

                               @Valid @RequestBody Course newCourse) {
        Course oldCourse = courseService.getCourse(id);
        if(oldCourse == null){
            throw new ResourceNotFoundException("Course", "id", id);
        }
        return courseService.updateCourse(oldCourse, newCourse);
    }

    /*------------------------------------ DELETE A SINGLE COURSE ----------------------------------- */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long id) {
        Course course = courseService.getCourse(id);
        if(course == null){
            throw new ResourceNotFoundException("Course", "id", id);
        }
        courseService.deleteCourse(course);
        return ResponseEntity.ok().build();
    }
    /*------------------------------------ DELETE A SINGLE COURSE ----------------------------------- */
    private File uploadFile(String type, MultipartFile file, Course course) throws IOException {
        String path = "";
        if(type.equals(FileTypes.IMAGE.name())){
            path = course.getImageDirPath();
        }else if(type.equals(FileTypes.VIDEO.name())){
            path = course.getTrailerVideoDirPath();
        }
        fileService.setPath(path);
        return fileService.createFile(file);
    }
}