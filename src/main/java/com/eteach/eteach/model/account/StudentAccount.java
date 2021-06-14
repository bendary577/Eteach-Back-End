package com.eteach.eteach.model.account;

import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("student_account")
@JsonIgnoreProperties({"courses","quizzes","ratings","course_request"})
public class StudentAccount extends Account implements Serializable {

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private Grade grade;

    @OneToMany(mappedBy = "student",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("courses")
    Set<StudentCourse> courses = new HashSet<>();

    @OneToMany(mappedBy = "student",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("quizzes")
    Set<StudentQuiz> quizzes = new HashSet<>();

    @OneToMany(mappedBy = "student",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("ratings")
    Set<CourseRating> ratings = new HashSet<>();

    @OneToMany(mappedBy="student",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("course_request")
    private List<CourseRequest> courseRequest;


    public StudentAccount() { }

    public StudentAccount(@JsonProperty("address")String address,
                          @JsonProperty("grade")Grade grade){
        this.address = address;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Set<StudentCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<StudentCourse> courses) {
        this.courses = courses;
    }

    public Set<StudentQuiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<StudentQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Set<CourseRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<CourseRating> ratings) {
        this.ratings = ratings;
    }

    public List<CourseRequest> getCourseRequest() {
        return courseRequest;
    }

    public void setCourseRequest(List<CourseRequest> courseRequest) {
        this.courseRequest = courseRequest;
    }
}
