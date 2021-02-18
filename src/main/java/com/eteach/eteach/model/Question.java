package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name="question")
@EntityListeners(AuditingEntityListener.class)
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
        private Quiz quiz;

    @OneToMany(mappedBy="question",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Choice> choices;

    public Question() { }

    public Question(@JsonProperty("id")Long id, @JsonProperty("first_name") String title){

    }

}
