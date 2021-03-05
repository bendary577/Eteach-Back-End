package com.eteach.eteach.model.manyToManyRelations;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.quiz.Quiz;

import javax.persistence.*;

@Entity
@Table(name="student_quiz")
public class StudentQuiz {

    @EmbeddedId
    StudentQuizKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    StudentAccount student;

    @ManyToOne
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    Quiz quiz;

    @Column
    float mark;

    public StudentQuiz(){}

    public StudentQuiz(StudentQuizKey id, StudentAccount student, Quiz quiz, float mark) {
        this.id = id;
        this.student = student;
        this.quiz = quiz;
        this.mark = mark;
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

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
