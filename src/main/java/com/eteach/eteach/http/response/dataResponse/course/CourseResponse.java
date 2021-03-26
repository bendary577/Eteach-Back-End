package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Course;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CourseResponse extends ApiResponse {

    Course data;

    public CourseResponse(){}

    public CourseResponse(HttpStatus status, String message, Course course){
        super(status, message);
        this.data = course;
    }

    public Course getData() {
        return data;
    }

    public void setData(Course data) {
        this.data = data;
    }
}
