package com.eteach.eteach.api.rest.course;
import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;
import com.eteach.eteach.enums.Rating;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.request.dataRequest.course.AddCourseRequest;
import com.eteach.eteach.http.request.dataRequest.course.GenerateCourseCodeRequest;
import com.eteach.eteach.http.request.dataRequest.course.RatingCourseRequest;
import com.eteach.eteach.http.request.dataRequest.course.SubscribeToCourseRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.course.*;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.account.User;
import com.eteach.eteach.model.compositeKeys.CourseRatingKey;
import com.eteach.eteach.model.compositeKeys.StudentCourseKey;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import com.eteach.eteach.model.course.WhatWillStudentLearn;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.file.Video;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import com.eteach.eteach.service.*;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/courses", produces = "application/json;charset=UTF-8")
public class CourseController {

    private final CourseService courseService;
    private final FileService fileService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final ApplicationUserService applicationUserService;
    private final WhatWillStudentLearnService whatWillStudentLearnService;
    private final StudentCourseService studentCourseService;
    private final CourseRatingService courseRatingService;

    @Autowired
    public CourseController(CourseService courseService,
                            FileService fileService,
                            AccountService accountService,
                            CategoryService categoryService,
                            ApplicationUserService applicationUserService,
                            WhatWillStudentLearnService whatWillStudentLearnService,
                            StudentCourseService studentCourseService,
                            CourseRatingService courseRatingService) {
        this.courseService = courseService;
        this.fileService = fileService;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.applicationUserService = applicationUserService;
        this.whatWillStudentLearnService = whatWillStudentLearnService;
        this.studentCourseService = studentCourseService;
        this.courseRatingService = courseRatingService;
    }

    /*------------------------------------ CREATE A NEW COURSE ---------------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> postCourse(@Valid @RequestBody AddCourseRequest addCourseRequest) {

        //SET COURSE INFO
        Course course = new Course();
        course.setName(addCourseRequest.getName());
        course.setDescription(addCourseRequest.getDescription());
        course.setPrice(addCourseRequest.getPrice());

        for(Grade grade : Grade.values()){
            if(addCourseRequest.getGrade().equals(grade.toString())){
                course.setGrade(grade);
            }
        }

        for(LevelOfDifficulty difficulty : LevelOfDifficulty.values()){
            if(addCourseRequest.getDifficulty_level().equals(difficulty.toString())){
                course.setDifficulty_level(difficulty);
            }
        }

        //ASSIGN TO TEACHER
        User user = this.applicationUserService.getUserByUsername(addCourseRequest.getTeacherName().trim());
        TeacherAccount teacher = (TeacherAccount) user.getAccount();
        course.setTeacherAccount(teacher);
        teacher.getCourses().add(course);

        //ASSIGN TO CATEGORY
        Category category = this.categoryService.getCategoryByName(addCourseRequest.getCategory());
        category.getCourses().add(course);
        course.setCategory(category);

        //SAVE INFO IN DATABASE
        this.courseService.saveCourse(course);
        for(String learn_sentence : addCourseRequest.getWhat_yow_will_learn()){
            System.out.println("learn sentence : " + learn_sentence);
            WhatWillStudentLearn learn = new WhatWillStudentLearn();
            learn.setSentence(learn_sentence);
            learn.setCourse(course);
            this.whatWillStudentLearnService.saveSentence(learn);
            course.getWhat_you_will_learn().add(learn);
        }

        this.accountService.saveTeacher(teacher);
        this.categoryService.saveCategory(category);

        //RETURN API RESPONSE
        return ResponseEntity.ok(new CourseRegisteredResponse(HttpStatus.OK, "course saved successfully by :" + teacher.getUser().getUsername(), course.getId()));
    }

    /*------------------------------------ UPLOAD COURSE TRAILER VIDEO ------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADMIN_TRAINEE')")
    @PostMapping(value = "/upload/{id}/video", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseVideo(@PathVariable(value = "id") Long id,
                                               @Valid @NotNull @NotEmpty @RequestPart("course_video") MultipartFile video) throws IOException {
        Course course = courseService.getCourse(id);
        if (course == null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "course not found"));
        }
        if(video == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error, we can't process video"));
        }
        String contentType = video.getContentType();
        Long size = video.getSize();
        if (!fileService.validateVideoFile(contentType, size)) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "trailer video is not valid"));
        }
        Path path = Paths.get("D:", "projects","E-Teach - Front End","dist","assets", "videos", "courses", course.getId().toString());
        String absolutePath = path.toFile().getAbsolutePath();
        Video course_video = fileService.createVideoFile(video, path);
        course.setTrailer_video(course_video);
        course_video.setCourse(course);
        this.courseService.saveCourse(course);
        this.fileService.saveVideo(course_video);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "trailer video uploaded successfully"));
    }

    /*------------------------------------ UPLOAD COURSE THUMBNAIL ------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADMIN_TRAINEE')")
    @PostMapping(value = "/upload_thumbnail/{id}/", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadCourseThumbnail(@PathVariable(value = "id") Long id,
                                                   @RequestPart("course_thumbnail") @Valid @NotNull @NotEmpty MultipartFile thumbnail) throws IOException {
        Course course = courseService.getCourse(id);
        if (course == null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "course not found"));
        }
        if(thumbnail == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error, we can't process video"));
        }
        String contentType = thumbnail.getContentType();
        Long size = thumbnail.getSize();
        if (!fileService.validateImageFile(contentType, size)) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "thumbnail is not valid"));
        }
        Path path = Paths.get("D:", "projects","E-Teach - Front End","dist","assets", "images", "courses", course.getId().toString(), "thumbnail");
        //String absolutePath = path.toFile().getAbsolutePath();
        Image image = fileService.createImageFile(thumbnail, path);
        course.setThumbnail(image);
        image.setCourse(course);
        this.courseService.saveCourse(course);
        this.fileService.saveImage(image);
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

    //------------------------- GET ALL SPECIFIC TEACHER COURSES -------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    @GetMapping("/teacher/{id}/")
    public ResponseEntity<?> getTeacherCourses(@PathVariable(value = "id") Long id) {
        TeacherAccount teacherAccount = accountService.getTeacher(id);
        if(teacherAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "teacher is not found"));
        }
        List<Course> courses = teacherAccount.getCourses();
        if(courses.size() == 0){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "teacher " + teacherAccount.getUser().getUsername() + " has no courses"));
        }
        return ResponseEntity.ok(new CoursesResponse(HttpStatus.OK, "courses returned successfully", courses));
    }

    /*------------------------------------ GET A SINGLE COURSE ----------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER','ROLE_STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable(value = "id") Long id) {
        Course course = courseService.getCourse(id);
        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT, "there is an error returning the course"));
        }
        return ResponseEntity.ok(new CourseResponse(HttpStatus.OK, "course returned successfully", course));
    }
    /*------------------------------------ RATE A COURSE ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/rate/{id}/")
    public ResponseEntity<?> rateCourse(@PathVariable(value = "id") Long id,
                           @Valid @RequestBody RatingCourseRequest ratingCourseRequest) {

        Course courseObject = this.courseService.getCourse(id);
        if(courseObject == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting dersired course"));
        }

        StudentAccount studentAccount = this.accountService.getStudent(ratingCourseRequest.getStudentId());
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired student account"));
        }

        //generate new course rating key
        CourseRatingKey courseRatingKey = new CourseRatingKey();
        courseRatingKey.setCourseId(courseObject.getId());
        courseRatingKey.setStudentId(studentAccount.getId());

        //make new course rating instance
        CourseRating courseRating = new CourseRating();
        courseRating.setId(courseRatingKey);
        courseRating.setCourse(courseObject);
        courseRating.setStudent(studentAccount);
        courseRating.setRating(ratingCourseRequest.getRating());

        //increment number of ratings to course
        courseObject.setRatings_number(courseObject.getRatings_number()+1);

        int ratingSummation = 0;
        for(CourseRating courseRating1 : courseObject.getRatings()){
            ratingSummation += courseRating1.getRating();
        }

        int rating = ratingSummation / courseObject.getRatings_number();

        for(Rating rating1 : Rating.values()){
            if(rating > rating1.getRatingCode()){
                courseObject.setRating(rating1);
                break;
            }
        }

        this.courseService.saveCourse(courseObject);
        this.courseRatingService.saveCourseRating(courseRating);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "student " + studentAccount.getUser().getUsername() + " have successfully rated this course !"));
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

    /*------------------------------ RETURN COURSE WITH ALL SECTIONS ------------------------------*/


}