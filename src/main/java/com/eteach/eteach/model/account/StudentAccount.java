package com.eteach.eteach.model.account;


import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("student_account")
public class StudentAccount extends Account implements Serializable {

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private Grade grade;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "student_course",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    @JsonIgnoreProperties("students")
    Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "student",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties("quizzes")
    Set<StudentQuiz> quizzes = new HashSet<>();

    @OneToMany(mappedBy = "student",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties("ratings")
    Set<CourseRating> ratings = new HashSet<>();

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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
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
}
