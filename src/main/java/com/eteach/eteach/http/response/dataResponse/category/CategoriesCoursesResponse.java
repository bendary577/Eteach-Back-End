package com.eteach.eteach.http.response.dataResponse.category;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.category.util.Category;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoriesCoursesResponse extends ApiResponse {

    private List<Category> data;

    public CategoriesCoursesResponse(){}

    public CategoriesCoursesResponse(HttpStatus status, String message,List<Category> data){
        super(status, message);
        this.data = data;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
