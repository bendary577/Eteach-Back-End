package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.model.course.Course;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CoursesResponse extends ApiResponse {
    List<Course> data;

    public CoursesResponse(){}

    public CoursesResponse(HttpStatus status, String message, List<Course> courses){
        super(status, message);
        this.data = courses;
    }

    public List<Course> getData() {
        return data;
    }

    public void setData(List<Course> dataList) {
        this.data = dataList;
    }
}
