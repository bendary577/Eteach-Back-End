package com.eteach.eteach.http.response.dataResponse.teacher;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.course.Category;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TeachersResponse extends ApiResponse {

    List<TeacherAccount> data;

    public TeachersResponse(){}

    public TeachersResponse(HttpStatus status, String message, List<TeacherAccount> categories){
        super(status, message);
        this.data = categories;
    }

    public List<TeacherAccount> getData() {
        return data;
    }

    public void setData(List<TeacherAccount> dataList) {
        this.data = dataList;
    }
}
