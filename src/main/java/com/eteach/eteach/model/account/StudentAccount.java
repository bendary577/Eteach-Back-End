package com.eteach.eteach.model.account;


import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.quiz.Quiz;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="student_accounts")
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("student_account")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class StudentAccount extends Account implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

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
    Set<Course> courses = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "student_quiz",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "quiz_id") }
    )
    Set<Quiz> quizzes = new HashSet<>();



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

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }


}
