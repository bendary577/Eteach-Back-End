package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.course.utils.CourseRequestUtil;
import org.springframework.http.HttpStatus;

public class GetCourseRequestByCodeRequest extends ApiResponse {

    private CourseRequestUtil data;

    public GetCourseRequestByCodeRequest(HttpStatus status, String message, CourseRequestUtil courseRequestUtil){
        super(status, message);
        this.data = courseRequestUtil;
    }

    public CourseRequestUtil getData() {
        return data;
    }

    public void setData(CourseRequestUtil data) {
        this.data = data;
    }
}
