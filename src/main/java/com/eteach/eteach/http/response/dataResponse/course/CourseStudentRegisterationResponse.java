package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class CourseStudentRegisterationResponse extends ApiResponse {

    private String courseName;
    private Long courseId;
    private String studentName;
    private Long studentId;

    public CourseStudentRegisterationResponse(HttpStatus status,
                                              String message,
                                              String courseName,
                                              Long courseId,
                                              String studentName,
                                              Long studentId){
        super(status, message);
        this.courseName = courseName;
        this.courseId = courseId;
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
