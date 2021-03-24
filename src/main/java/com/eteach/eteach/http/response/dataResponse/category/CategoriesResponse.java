package com.eteach.eteach.http.response.dataResponse.category;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.course.Category;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoriesResponse extends ApiResponse {

    List<Category> data;

    public CategoriesResponse(){}

    public CategoriesResponse(HttpStatus status, String message, List<Category> categories){
        super(status, message);
        this.data = categories;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> dataList) {
        this.data = dataList;
    }

}
