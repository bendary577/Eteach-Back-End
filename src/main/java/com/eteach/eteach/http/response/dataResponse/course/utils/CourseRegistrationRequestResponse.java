package com.eteach.eteach.http.response.dataResponse.course.utils;

import com.eteach.eteach.http.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class CourseRegistrationRequestResponse extends ApiResponse {

    private String courseRegistrationCode;

    public CourseRegistrationRequestResponse(HttpStatus status, String message, String courseRegistrationCode){
        super(status, message);
        this.courseRegistrationCode = courseRegistrationCode;
    }

    public String getCourseRegistrationCode() {
        return courseRegistrationCode;
    }

    public void setCourseRegistrationCode(String courseRegistrationCode) {
        this.courseRegistrationCode = courseRegistrationCode;
    }
}
