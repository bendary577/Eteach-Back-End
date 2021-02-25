package com.eteach.eteach.model.quiz;

import com.eteach.eteach.enums.LevelOfDifficulty;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="quizzes")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank
    @Column(nullable = false, length = 100)
    private LevelOfDifficulty difficulty_level;

    @NotBlank
    @Column(nullable = false, length = 100)
    private int questions_number;

    @NotBlank
    @Column(nullable = false, length = 100)
    private int final_grade;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @OneToMany(mappedBy="quiz",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Question> questions;

    @ManyToMany(mappedBy = "quizzes")
    private Set<StudentAccount> students = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Course course;

    public Quiz() { }

    public Quiz(@JsonProperty("title")String title, @JsonProperty("difficult_level")LevelOfDifficulty difficulty_level,  @JsonProperty("questions_number")int questions_number,  @JsonProperty("final_grade")int final_grade) {
        this.id = id;
        this.title = title;
        this.difficulty_level = difficulty_level;
        this.questions_number = questions_number;
        this.final_grade = final_grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LevelOfDifficulty getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(LevelOfDifficulty difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public int getQuestions_number() {
        return questions_number;
    }

    public void setQuestions_number(int questions_number) {
        this.questions_number = questions_number;
    }

    public int getFinal_grade() {
        return final_grade;
    }

    public void setFinal_grade(int final_grade) {
        this.final_grade = final_grade;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Set<StudentAccount> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentAccount> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
