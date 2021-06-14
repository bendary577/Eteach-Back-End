package com.eteach.eteach.http.request.dataRequest.quiz;

import java.io.Serializable;
import java.util.List;

public class AddQuizQuestionsRequest implements Serializable {

    private String text;
    private List<String> choices;
    private Integer answer;
    private boolean hasImage;
    private int questionNumber;

    public AddQuizQuestionsRequest(){}

    public AddQuizQuestionsRequest(String text,
                                   List<String> choices,
                                   Integer answer,
                                   boolean hasImage,
                                   int questionNumber){
        this.text = text;
        this.choices = choices;
        this.answer = answer;
        this.hasImage = hasImage;
        this.questionNumber = questionNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
