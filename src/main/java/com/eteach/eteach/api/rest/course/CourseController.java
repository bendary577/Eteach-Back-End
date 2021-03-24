package com.eteach.eteach.api.rest.course;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.request.AddCourseRequest;
import com.eteach.eteach.http.request.SubscribeToCourseRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.file.Video;
import com.eteach.eteach.service.AccountService;
import com.eteach.eteach.service.CategoryService;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/courses", produces = "application/json;charset=UTF-8")
public class CourseController {

    private final CourseService courseService;
    private final FileService fileService;
    private final AccountService accountService;
    private final CategoryService categoryService;

    @Autowired
    public CourseController(CourseService courseService,
                            FileService fileService,
                            AccountService accountService,
                            CategoryService categoryService) {
        this.courseService = courseService;
        this.fileService = fileService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    /*------------------------------------ CREATE A NEW COURSE ---------------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @PostMapping("/")
    public ResponseEntity<?> postCourse(@Valid @RequestBody AddCourseRequest addCourseRequest) {
        //SET COURSE INFO
        Course course = new Course();
        course.setName(addCourseRequest.getName());
        course.setDescription(addCourseRequest.getDescription());
        course.setPrice(addCourseRequest.getPrice());
        course.setGrade(addCourseRequest.getGrade());
        //course.setWhat_yow_will_learn(addCourseRequest.getWhat_yow_will_learn());
        course.setDifficulty_level(addCourseRequest.getDifficulty_level());
        //ASSIGN TO TEACHER
        TeacherAccount teacher = accountService.getTeacher(addCourseRequest.getTeacherId());
        course.setTeacher(teacher);
        teacher.getCourses().add(course);
        //ASSIGN TO CATEGORY
        Category category = categoryService.getCategoryByName(addCourseRequest.getCategory());
        category.getCourses().add(course);
        course.setCategory(category);
        //SAVE INFO IN DATABASE
        this.courseService.saveCourse(course);
        this.accountService.saveTeacher(teacher);
        this.categoryService.saveCategory(category);
        //RETURN API RESPONSE
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "course saved successfully by :" + teacher.getUser().getUsername()));
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

        Path path = Paths.get(course.getTrailerVideoDirPath());
        Video trailer_video = fileService.createVideoFile(video, path);
        course.setTrailer_video(trailer_video);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "trailer video uploaded successfully"));
    }

    /*------------------------------------ UPLOAD COURSE THUMBNAIL ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/upload/{id}/thumbnail/", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseThumbnail(@PathVariable(value = "id") Long id,
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
        Path path = Paths.get(course.getThumbnailDirPath());
        Image image = fileService.createImageFile(thumbnail, path);
        course.setThumbnail(image);
        image.setCourse(course);
        courseService.saveCourse(course);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "course thumbnail uploaded successfully"));
    }

    /*------------------------------------ GET ALL COURSES WITH PAGINATION ------------------------------ */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/")
    public List<Course> getAllCourses(@RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return courseService.getCourses(pageNo, pageSize, sortBy);
    }

    /*------------------------------------ GET A SINGLE COURSE ----------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')")
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
        System.out.println("student id :" + subscribeToCourseRequest.getStudentId());
        //GET STUDENT
        Long studentId = subscribeToCourseRequest.getStudentId();
        StudentAccount student = accountService.getStudent(studentId);
        String studentUsername = student.getUser().getUsername();
        //GET COURSE
        Course course = courseService.getCourse(id);
        String courseName = course.getName();
        //ASSIGN STUDENT TO COURSE
        student.getCourses().add(course);
        course.getStudents().add(student);
        //INCREMENT COURSE STUDENT NUMBER
        course.setStudents_number(course.getStudents_number() + 1);
        //SAVE INFO IN DATABASE
        courseService.saveCourse(course);
        accountService.saveStudent(student);
        //RETURN API RESPONSE
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_TEACHER')")
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