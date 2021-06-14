package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class CourseCodeResponse extends ApiResponse {

    private String courseCode;
    private String studentName;
    private Long studentId;

    public CourseCodeResponse(HttpStatus status,
                              String message,
                              String courseCode,
                              String studentName,
                              Long studentId){
        super(status, message);
        this.courseCode = courseCode;
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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
