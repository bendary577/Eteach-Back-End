package com.eteach.eteach.event;

import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.context.ApplicationEvent;

public class QuizAddedEvent extends ApplicationEvent {

    private Quiz quiz;
    private String teacherName;
    private String courseName;


    public QuizAddedEvent(Object source, String teacherName, String courseName, Quiz quiz) {
        super(source);
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
