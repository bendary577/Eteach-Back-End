package com.eteach.eteach.http.response.dataResponse.course;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import org.springframework.http.HttpStatus;

public class CourseRequestResponse extends ApiResponse {

    private CourseRequest data;

    public CourseRequestResponse(HttpStatus status, String message, CourseRequest courseRequest){
        super(status, message);
        this.data = courseRequest;
    }

    public CourseRequest getData() {
        return data;
    }

    public void setData(CourseRequest data) {
        this.data = data;
    }
}
