package com.eteach.eteach.model;

import com.eteach.eteach.enums.LevelOfDifficulty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="quiz")
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

    @OneToMany(mappedBy="quiz",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Question> questions;

    public Quiz() { }

    public Quiz(@JsonProperty("id")Long id, @JsonProperty("title")String title, @JsonProperty("difficult_level")LevelOfDifficulty difficulty_level,  @JsonProperty("questions_number")int questions_number,  @JsonProperty("final_grade")int final_grade) {
        this.id = id;
        this.title = title;
        this.difficulty_level = difficulty_level;
        this.questions_number = questions_number;
        this.final_grade = final_grade;
    }



}
