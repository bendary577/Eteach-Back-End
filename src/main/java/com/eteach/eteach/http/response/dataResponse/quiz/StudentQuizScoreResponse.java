package com.eteach.eteach.http.response.dataResponse.quiz;

import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.http.HttpStatus;

public class StudentQuizScoreResponse extends ApiResponse {

    private int score;
    private Quiz quiz;

    public StudentQuizScoreResponse(HttpStatus status, String message, int score, Quiz quiz){
        super(status, message);
        this.score = score;
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
