package com.eteach.eteach.http.response.dataResponse.string;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Course;
import org.springframework.http.HttpStatus;

import java.util.List;

public class StringsResponse extends ApiResponse {
    List<String> data;

    public StringsResponse(){}

    public StringsResponse(HttpStatus status, String message, List<String> strings){
        super(status, message);
        this.data = strings;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> dataList) {
        this.data = dataList;
    }
}
