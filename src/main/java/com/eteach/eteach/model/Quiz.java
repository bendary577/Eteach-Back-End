package com.eteach.eteach.model;

import com.eteach.eteach.enums.LevelOfDifficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="quiz")
@EntityListeners(AuditingEntityListener.class)
public class Quiz implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 100)
    private Date date;

    @NotBlank
    @Column(nullable = false, length = 100)
    private LevelOfDifficulty levelOfDifficulty;

    @NotBlank
    @Column(nullable = false, length = 100)
    private int Questions_number;

    @NotBlank
    @Column(nullable = false, length = 100)
    private int Completion_points;


    public Quiz() { }

    public Quiz(@JsonProperty("id")Long id, @JsonProperty("first_name") String title){

    }

}
