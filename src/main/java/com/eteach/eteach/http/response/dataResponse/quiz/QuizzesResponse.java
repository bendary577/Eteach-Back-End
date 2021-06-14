package com.eteach.eteach.http.response.dataResponse.quiz;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.http.HttpStatus;

import java.util.List;

public class QuizzesResponse extends ApiResponse {

    private List<Quiz> data;

    public QuizzesResponse(HttpStatus status, String message, List<Quiz> data){
        super(status, message);
        this.data = data;
    }

    public List<Quiz> getData() {
        return data;
    }

    public void setData(List<Quiz> data) {
        this.data = data;
    }
}
