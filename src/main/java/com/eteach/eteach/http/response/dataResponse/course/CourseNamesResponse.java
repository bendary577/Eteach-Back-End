package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.course.utils.CoursesNames;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CourseNamesResponse extends ApiResponse {

    List<CoursesNames> coursesNamesList;

    public CourseNamesResponse(){}

    public CourseNamesResponse(HttpStatus status, String message){
        super(status, message);
    }

    public List<CoursesNames> getCoursesNamesList() {
        return coursesNamesList;
    }

    public void setCoursesNamesList(List<CoursesNames> coursesNamesList) {
        this.coursesNamesList = coursesNamesList;
    }
}
