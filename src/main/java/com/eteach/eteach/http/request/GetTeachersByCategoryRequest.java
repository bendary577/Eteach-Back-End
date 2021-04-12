package com.eteach.eteach.http.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class GetTeachersByCategoryRequest implements Serializable {

    @NotBlank
    private String categoryName;

    public GetTeachersByCategoryRequest(){}

    public GetTeachersByCategoryRequest(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
