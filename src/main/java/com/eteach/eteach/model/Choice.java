package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="choices")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
