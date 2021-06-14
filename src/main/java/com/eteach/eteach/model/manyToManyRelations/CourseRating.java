package com.eteach.eteach.model.manyToManyRelations;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.CourseRatingKey;
import com.eteach.eteach.model.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="course_rating")
@JsonIgnoreProperties(value = {"student", "course"})
public class CourseRating {

    @EmbeddedId
    CourseRatingKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @JsonProperty("student")
    StudentAccount student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @JsonProperty("course")
    Course course;

    @Column
    private int rating;

    public CourseRating(){}

    public CourseRating(CourseRatingKey id, StudentAccount student, Course course, int rating) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.rating = rating;
    }

    public CourseRatingKey getId() {
        return id;
    }

    public void setId(CourseRatingKey id) {
        this.id = id;
    }

    public StudentAccount getStudent() {
        return student;
    }

    public void setStudent(StudentAccount student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
