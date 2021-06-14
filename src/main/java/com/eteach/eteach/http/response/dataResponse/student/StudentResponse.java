package com.eteach.eteach.http.response.dataResponse.student;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.account.StudentAccount;
import org.springframework.http.HttpStatus;

public class StudentResponse extends ApiResponse {

    private StudentAccount data;

    public StudentResponse(HttpStatus status, String message, StudentAccount studentAccount){
        super(status, message);
        this.data = studentAccount;
    }

    public StudentAccount getData() {
        return data;
    }

    public void setData(StudentAccount data) {
        this.data = data;
    }
}
