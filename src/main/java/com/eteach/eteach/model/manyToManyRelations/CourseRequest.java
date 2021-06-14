package com.eteach.eteach.model.manyToManyRelations;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentCourseKey;
import com.eteach.eteach.model.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="course_request")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"student", "course"})
public class CourseRequest {

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

    @Column(unique = true)
    private String request_code;

    public CourseRequest(){}

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

    public String getRequest_code() {
        return request_code;
    }

    public void setRequest_code(String request_code) {
        this.request_code = request_code;
    }
}
