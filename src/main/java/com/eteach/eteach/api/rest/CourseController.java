package com.eteach.eteach.api.rest;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.request.SubscribeToCourseRequest;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.file.Video;
import com.eteach.eteach.service.AccountService;
import com.eteach.eteach.service.CourseService;
import com.eteach.eteach.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final AccountService accountService;

    @Autowired
    public CourseController(CourseService courseService,
                            FileService fileService,
                            AccountService accountService) {
        this.courseService = courseService;
        this.fileService = fileService;
        this.accountService = accountService;
    }

    /*------------------------------------ CREATE A NEW COURSE ---------------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    @PostMapping("/")
    public ResponseEntity<?> postCourse(@Valid @RequestBody Course course) {
        this.courseService.createCourse(course);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "course saved successfully"));
    }

    /*------------------------------------ UPLOAD COURSE TRAILER VIDEO ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/upload/{id}/video/", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseVideo(@PathVariable(value = "id") Long id,
                                               @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile video) throws IOException {
        Course course = courseService.getCourse(id);
        if (course == null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "course not found"));
        }
        String contentType = video.getContentType();
        Long size = video.getSize();
        if (!fileService.validateVideoFile(contentType, size)) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "trailer video is not valid"));
        }

        String path = course.getTrailerVideoDirPath();
        fileService.setPath(path);
        Video trailer_video = fileService.createVideoFile(video);
        course.setTrailer_video(trailer_video);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "trailer video uploaded successfully"));
    }

    /*------------------------------------ UPLOAD COURSE THUMBNAIL ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/upload/{id}/thumbnail/", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseThumbnail(@PathVariable(value = "id") Long id,
                                                   @PathVariable(value = "type") String type,
                                                   @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile thumbnail) throws IOException {
        Course course = courseService.getCourse(id);
        String contentType = thumbnail.getContentType();
        Long size = thumbnail.getSize();
        if (course == null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "course not found"));
        }
        if (!fileService.validateVideoFile(contentType, size)) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "thumbnail is not valid"));
        }
        String path = course.getThumbnailDirPath();
        fileService.setPath(path);
        Image image = fileService.createImageFile(thumbnail);
        course.setThumbnail(image);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "course thumbnail uploaded successfully"));
    }

    /*------------------------------------ GET ALL COURSES WITH PAGINATION ------------------------------ */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    @GetMapping("/")
    public List<Course> getAllCourses(@RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        System.out.println("i'm here with ROLE_TEACHER");
        return courseService.getAllCourses(pageNo, pageSize);
    }

    /*------------------------------------ GET A SINGLE COURSE ----------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable(value = "id") Long id) {
        return courseService.getCourse(id);
    }

    /*------------------------------------ RATE A COURSE ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("rate/{id}")
    public void rateCourse(@PathVariable(value = "id") Long id,
                             @Valid @RequestBody Course newCourse) {
        System.out.println("rate course");
    }

    /*------------------------------------ SUBSCRIBE TO A COURSE ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("subscribe/{id}")
    public ResponseEntity<?> subscribeToCourse(@PathVariable(value = "id") Long id,
                                  @Valid @RequestBody SubscribeToCourseRequest subscribeToCourseRequest) {
        Long studentId = subscribeToCourseRequest.getStudentId();
        Long courseId = subscribeToCourseRequest.getCourseId();
        StudentAccount student = accountService.getStudent(studentId);
        String studentUsername = student.getUser().getUsername();
        Course course = courseService.getCourse(courseId);
        String courseName = course.getName();
        student.getCourses().add(course);
        course.getStudents().add(student);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "student" + studentUsername + "subscribed to course" + courseName + "successfully"));
    }

    /*------------------------------------ UPDATE COURSE INFO ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable(value = "id") Long id,
                               @Valid @RequestBody Course newCourse) {
        Course oldCourse = courseService.getCourse(id);
        if (oldCourse == null) {
            throw new ResourceNotFoundException("Course", "id", id);
        }
        return courseService.updateCourse(oldCourse, newCourse);
    }

    /*------------------------------------ DELETE A SINGLE COURSE ----------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long id) {
        Course course = courseService.getCourse(id);
        if (course == null) {
            throw new ResourceNotFoundException("Course", "id", id);
        }
        courseService.deleteCourse(course);
        return ResponseEntity.ok().build();
    }

    /*--------------------------------------- ADD COURSE SECTION --------------------------------------*/

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/{id}/section/")
    public ResponseEntity<?> addCourseSection(@PathVariable(value = "id") Long courseId,
                                                   @PathVariable(value = "type") String type,
                                                   @RequestPart("content") @Valid @NotNull @NotEmpty MultipartFile thumbnail) throws IOException {

        return ResponseEntity.ok().build();
    }

    /*--------------------------------- ADD COURSE LESSON TO SECTION -----------------------------*/


    /*------------------------------ RETURN COURSE WITH ALL SECTIONS ------------------------------*/


}