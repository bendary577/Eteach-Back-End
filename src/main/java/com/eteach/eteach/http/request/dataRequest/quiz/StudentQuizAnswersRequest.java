package com.eteach.eteach.http.request.dataRequest.quiz;

import java.io.Serializable;
import java.util.List;

public class StudentQuizAnswersRequest implements Serializable {

    private List<Integer> studentAnswers;
    private Long studentId;

    public StudentQuizAnswersRequest(){}

    public StudentQuizAnswersRequest(List<Integer> studentAnswers, Long studentId){
        this.studentAnswers = studentAnswers;
        this.studentId = studentId;
    }

    public List<Integer> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(List<Integer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
