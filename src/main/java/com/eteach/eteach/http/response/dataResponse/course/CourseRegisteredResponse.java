package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class CourseRegisteredResponse extends ApiResponse {

    private Long courseId;

    public CourseRegisteredResponse(HttpStatus status,
                                    String message,
                                    Long courseId){
        super(status, message);
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

}
