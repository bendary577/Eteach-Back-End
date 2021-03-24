package com.eteach.eteach.http.response.dataResponse.category;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Category;
import org.springframework.http.HttpStatus;

public class CategoryResponse extends ApiResponse {

    Category data;

    public CategoryResponse(){}

    public CategoryResponse(HttpStatus status, String message, Category category){
        super(status, message);
        this.data = category;
    }

    public Category getData() {
        return data;
    }

    public void setData(Category data) {
        this.data = data;
    }
}
