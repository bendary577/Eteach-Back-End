package com.eteach.eteach.model.quiz;

import com.eteach.eteach.enums.LevelOfDifficulty;
import com.eteach.eteach.model.course.Category;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="quizzes")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "questions", "students", "course"})
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;

    @NotNull
    @Column(nullable = false, length = 100)
    private LevelOfDifficulty difficulty_level;

    @NotNull
    @Column(nullable = false)
    private int final_grade;

    @NotNull
    @Column(nullable = false)
    private int questions_number;

    @Column
    private int applied_students_number;

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
    @JsonProperty("questions")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("students")
    private Set<StudentQuiz> students = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonProperty("course")
    private Course course;

    public Quiz() { }

    public Quiz(@JsonProperty("title")String title,
                @JsonProperty("difficult_level")LevelOfDifficulty difficulty_level,
                @JsonProperty("final_grade")int final_grade,
                @JsonProperty("questions_number")int questions_number) {
        this.id = id;
        this.title = title;
        this.difficulty_level = difficulty_level;
        this.final_grade = final_grade;
        this.questions_number = questions_number;
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

    public Set<StudentQuiz> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentQuiz> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getApplied_students_number() {
        return applied_students_number;
    }

    public void setApplied_students_number(int applied_students_number) {
        this.applied_students_number = applied_students_number;
    }

    public int getQuestions_number() {
        return questions_number;
    }

    public void setQuestions_number(int questions_number) {
        this.questions_number = questions_number;
    }
}
