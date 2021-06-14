package com.eteach.eteach.model.manyToManyRelations;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.quiz.Quiz;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="student_quiz")
@JsonIgnoreProperties(value = {"student", "quiz"})
public class StudentQuiz {

    @EmbeddedId
    StudentQuizKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @JsonProperty("student")
    StudentAccount student;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    @JsonProperty("quiz")
    Quiz quiz;

    @Column
    int score;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date submittedOn;

    public StudentQuiz(){}

    public StudentQuiz(StudentQuizKey id, StudentAccount student, Quiz quiz, int score, Date submittedOn) {
        this.id = id;
        this.student = student;
        this.quiz = quiz;
        this.score = score;
        this.submittedOn = submittedOn;
    }

    public StudentQuizKey getId() {
        return id;
    }

    public void setId(StudentQuizKey id) {
        this.id = id;
    }

    public StudentAccount getStudent() {
        return student;
    }

    public void setStudent(StudentAccount student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(Date submittedOn) {
        this.submittedOn = submittedOn;
    }
}
