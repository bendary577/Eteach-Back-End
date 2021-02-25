package com.eteach.eteach.model.file;

import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.quiz.Question;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image extends File{

    @OneToOne(mappedBy = "thumbnail")
    private Course course;

    @OneToOne(mappedBy = "image")
    private Question question;

    public Image() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
