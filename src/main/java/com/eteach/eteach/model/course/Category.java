package com.eteach.eteach.model.course;

import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="categories")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "courses", "teachers" })
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String description;


    @OneToMany(mappedBy="category",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("courses")
    private List<Course> courses;


    @OneToMany(mappedBy="subject",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("teachers")
    private List<TeacherAccount> teachers;

    public Category() { }

    public Category(@JsonProperty("id")Long id,
                    @JsonProperty("name") String name,
                    @JsonProperty("description")String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<TeacherAccount> getTeachers() {
        return teachers;
    }

    public void setTeacher(List<TeacherAccount> teachers) {
        this.teachers = teachers;
    }
}
