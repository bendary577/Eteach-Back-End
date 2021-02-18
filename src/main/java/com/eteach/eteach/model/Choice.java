package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="question")
@EntityListeners(AuditingEntityListener.class)
public class Choice implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String text;

    @NotBlank
    @Column(nullable = false, length = 100)
    private char letter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qustion_id", referencedColumnName = "id")
    private Question question;

    public Choice() { }

    public Choice(@JsonProperty("id")Long id, @JsonProperty("text") String text,
                  @JsonProperty("letter")char letter){

    }

}
