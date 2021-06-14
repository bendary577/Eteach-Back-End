package com.eteach.eteach.api.rest.course;

import com.eteach.eteach.http.request.dataRequest.course.CourseRegistrationRequest;
import com.eteach.eteach.http.request.dataRequest.course.GenerateCourseCodeRequest;
import com.eteach.eteach.http.request.dataRequest.course.SubscribeToCourseRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.course.CourseCodeResponse;
import com.eteach.eteach.http.response.dataResponse.course.CourseRequestResponse;
import com.eteach.eteach.http.response.dataResponse.course.CourseStudentRegisterationResponse;
import com.eteach.eteach.http.response.dataResponse.course.GetCourseRequestByCodeRequest;
import com.eteach.eteach.http.response.dataResponse.course.utils.CourseRegistrationRequestResponse;
import com.eteach.eteach.http.response.dataResponse.course.utils.CourseRequestUtil;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentCourseKey;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import com.eteach.eteach.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping(value ="/api/v1/course_request/", produces = "application/json;charset=UTF-8")
public class CourseRequestController {

    private final CourseRequestService courseRequestService;
    private final CourseService courseService;
    private final AccountService accountService;
    private final StudentCourseService studentCourseService;

    @Autowired
    public CourseRequestController(CourseRequestService courseRequestService,
                                   CourseService courseService,
                                   AccountService accountService,
                                   StudentCourseService studentCourseService){
        this.courseRequestService = courseRequestService;
        this.courseService = courseService;
        this.accountService = accountService;
        this.studentCourseService = studentCourseService;
    }

    /*------------------------------------ MAKE COURSE REQUEST ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/{id}/")
    public ResponseEntity<?> courseRegistrationRequest(@PathVariable(value = "id") Long id,
                                                       @Valid @RequestParam(name="student_id") Long studentId) {
        Course course = this.courseService.getCourse(id);
        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired course"));
        }

        StudentAccount studentAccount = this.accountService.getStudent(studentId);
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired student account"));
        }

        StudentCourseKey studentCourseKey = new StudentCourseKey();
        studentCourseKey.setCourseId(course.getId());
        studentCourseKey.setStudentId(studentAccount.getId());

        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setId(studentCourseKey);
        courseRequest.setCourse(course);
        courseRequest.setStudent(studentAccount);
        String registrationCode = String.valueOf(this.courseRequestService.generateCourseRequestCode());

        courseRequest.setRequest_code(registrationCode);
        this.courseService.saveCourse(course);
        this.accountService.saveStudent(studentAccount);
        this.courseRequestService.saveCourseRequest(courseRequest);

        return ResponseEntity.ok(new CourseRegistrationRequestResponse(HttpStatus.OK, "registration request code returned successfully", registrationCode));
    }

    /*------------------------------------ MAKE COURSE REQUEST ------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTARAINEE','ROLE_STUDENT')")
    @GetMapping("/identify/{id}/")
    public ResponseEntity<?> identifyCourseRequestInfo(@PathVariable(value = "id") String id) {
        CourseRequest courseRequest = this.courseRequestService.getCourseRequestByCode(id);
        if(courseRequest == null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired course request"));
        }
        return ResponseEntity.ok(new CourseRequestResponse(HttpStatus.OK, "code request returned successfully", courseRequest));
    }

    /*------------------------------------ MAKE COURSE REQUEST ------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTARAINEE','ROLE_STUDENT')")
    @GetMapping("/get/{id}/")
    public ResponseEntity<?> getCourseRequest(@PathVariable(value = "id") String id) {
        CourseRequest courseRequest = this.courseRequestService.getCourseRequestByCode(id);
        if(courseRequest == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired course request"));
        }
        CourseRequestUtil courseRequestUtil = new CourseRequestUtil();
        courseRequestUtil.setCourseId(courseRequest.getId().getCourseId());
        courseRequestUtil.setStudentId(courseRequest.getId().getStudentId());
        courseRequestUtil.setCourse(courseRequest.getCourse());
        courseRequestUtil.setStudentAccount(courseRequest.getStudent());
        return ResponseEntity.ok(new GetCourseRequestByCodeRequest(HttpStatus.OK, "code request returned successfully", courseRequestUtil));
    }


    /*------------------------------------ SUBSCRIBE TO A COURSE ------------------------------------- */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    @GetMapping("/generateCode/{id}/")
    public ResponseEntity<?> getStudentCourseCode(@PathVariable(value = "id") Long id,
                                                  @Valid @RequestParam(name="student_id") Long studentId) {
        Course course = this.courseService.getCourse(id);
        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired course"));
        }

        StudentAccount studentAccount = this.accountService.getStudent(studentId);
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired student account"));
        }

        //check that there is no relation between student and desired course
        StudentCourse studentCourse = this.studentCourseService.getStudentCourse(studentAccount.getId(),course.getId());
        if(studentCourse != null && studentCourse.getStudent().getId().equals(studentAccount.getId()) && studentCourse.getCourse().getId().equals(course.getId())){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.IM_USED, "sorry, you can't register in a course you're already registered in"));
        }

        StudentCourseKey studentCourseKey = new StudentCourseKey();
        studentCourseKey.setStudentId(studentAccount.getId());
        studentCourseKey.setCourseId(course.getId());

        String studentCourseCode = this.courseService.generateCourseCode();
        StudentCourse studentCourse1 = new StudentCourse();
        studentCourse1.setId(studentCourseKey);
        studentCourse1.setStudent(studentAccount);
        studentCourse1.setCourse(course);
        studentCourse1.setCourseCode(studentCourseCode);
        studentCourse1.setRegisteredOn(new Date());

        this.courseService.saveCourse(course);
        this.accountService.saveStudent(studentAccount);
        this.studentCourseService.saveStudentCourse(studentCourse1);

        return ResponseEntity.ok(new CourseCodeResponse(HttpStatus.OK, "student course code returned successfully",
                studentCourseCode, studentAccount.getUser().getUsername(), studentAccount.getId()));
    }

    /*------------------------------------ SUBSCRIBE TO A COURSE ------------------------------------- */
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/subscribe/{id}/")
    public ResponseEntity<?> subscribeToCourse(@PathVariable(value = "id") Long id,
                                               @Valid @RequestBody SubscribeToCourseRequest subscribeToCourseRequest) {
        Course course = this.courseService.getCourse(id);
        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired course"));
        }

        StudentAccount studentAccount = this.accountService.getStudent(subscribeToCourseRequest.getStudentId());
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we have a problem getting desired student account"));
        }

        StudentCourse studentCourse = this.studentCourseService.getStudentCourse(studentAccount.getId(), course.getId());
        if(studentCourse == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "sorry, we can't find your registration info !"));
        }

        if(studentCourse.isRegistered()){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.IM_USED, "sorry, this course is already registered !"));
        }

        if(!studentCourse.getCourseCode().equals(subscribeToCourseRequest.getCourseCode())){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "sorry, the code you inserted is false !"));
        }
        studentCourse.setRegistered(true);

        this.studentCourseService.saveStudentCourse(studentCourse);

        return ResponseEntity.ok(new CourseStudentRegisterationResponse(HttpStatus.OK, "student " + studentAccount.getUser().getUsername() + " registered successfully to " + course.getName() + " course",
                course.getName(), course.getId(), studentAccount.getUser().getUsername(), studentAccount.getId()));
    }

}
