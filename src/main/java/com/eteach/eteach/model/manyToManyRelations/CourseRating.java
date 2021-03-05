package com.eteach.eteach.model.manyToManyRelations;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.CourseRatingKey;
import com.eteach.eteach.model.course.Course;

import javax.persistence.*;

@Entity
@Table(name="course_rating")
public class CourseRating {

    @EmbeddedId
    CourseRatingKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    StudentAccount student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    @Column
    private float rating;

    public CourseRating(){}

    public CourseRating(CourseRatingKey id, StudentAccount student, Course course, float rating) {
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
