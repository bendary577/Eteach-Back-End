package com.eteach.eteach.model.quiz;

import com.eteach.eteach.model.file.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="questions")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"choices","quiz"})
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String text;

    @Column(nullable = false)
    private int rightAnswerNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    @JsonProperty("quiz")
    private Quiz quiz;

    @OneToMany(mappedBy="question",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value= FetchMode.SUBSELECT)
    @JsonProperty("choices")
    private List<Choice> choices;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Question() {
        choices = new ArrayList<>();
    }

    public Question(@JsonProperty("first_name") String text, @JsonProperty("rightAnswerNumber") int rightAnswerNumber){
        this.text = text;
        this.rightAnswerNumber = rightAnswerNumber;
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

    public int getRightAnswerNumber() {
        return rightAnswerNumber;
    }

    public void setRightAnswerNumber(int rightAnswerNumber) {
        this.rightAnswerNumber = rightAnswerNumber;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /*
    @Transient
    public String getImageDirPath() {
        if (image == null || this.id == null) return null;

        return "/quiz-questions/" + this.id + "/" + image;
    }
    */

}
