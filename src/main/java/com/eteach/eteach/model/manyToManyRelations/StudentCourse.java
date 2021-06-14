package com.eteach.eteach.model.manyToManyRelations;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentCourseKey;
import com.eteach.eteach.model.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="student_course")
@JsonIgnoreProperties(value = {"student", "course"})
public class StudentCourse {
    @EmbeddedId
    StudentCourseKey id;

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
    String courseCode;

    @Column
    boolean registered;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date registeredOn;

    public StudentCourse(){}

    public StudentCourse(StudentCourseKey id, StudentAccount student, Course course, String courseCode, Date registeredOn) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.courseCode = courseCode;
        this.registeredOn = registeredOn;
    }

    public StudentCourseKey getId() {
        return id;
    }

    public void setId(StudentCourseKey id) {
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
