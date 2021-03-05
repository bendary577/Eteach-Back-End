package com.eteach.eteach.model.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StudentQuizKey implements Serializable {
    @Column(name = "student_id")
    Long studentId;

    @Column(name = "quiz_id")
    Long quizId;

  public StudentQuizKey(){}


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        quizId = quizId;
    }
}
