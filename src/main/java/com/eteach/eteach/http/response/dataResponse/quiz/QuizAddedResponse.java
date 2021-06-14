package com.eteach.eteach.http.response.dataResponse.quiz;

import com.eteach.eteach.http.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class QuizAddedResponse extends ApiResponse {

    private String quizTitle;
    private Long quizId;
    private Integer quiz_questions_number;

    public QuizAddedResponse(HttpStatus status, String message, String quizTitle, Long quizId, Integer quiz_questions_number){
        super(status, message);
        this.quizTitle = quizTitle;
        this.quizId = quizId;
        this.quiz_questions_number = quiz_questions_number;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Integer getQuiz_questions_number() {
        return quiz_questions_number;
    }

    public void setQuiz_questions_number(Integer quiz_questions_number) {
        this.quiz_questions_number = quiz_questions_number;
    }
}
