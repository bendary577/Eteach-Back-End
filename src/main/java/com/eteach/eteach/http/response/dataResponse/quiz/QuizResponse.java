package com.eteach.eteach.http.response.dataResponse.quiz;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.http.HttpStatus;

public class QuizResponse extends ApiResponse {

    private Quiz data;

    public QuizResponse(HttpStatus status, String message, Quiz quiz){
       super(status, message);
       this.data = quiz;
    }

    public Quiz getData() {
        return data;
    }

    public void setData(Quiz data) {
        this.data = data;
    }
}
