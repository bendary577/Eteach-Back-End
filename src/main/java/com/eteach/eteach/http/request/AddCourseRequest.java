package com.eteach.eteach.http.request;

import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddCourseRequest {

    private String name;
    private String description;
    private float price;
    private Grade grade;
    private List<String> what_yow_will_learn;
    private LevelOfDifficulty difficulty_level;
    private Long teacherId;
    private String category;

    public AddCourseRequest(){}

    public AddCourseRequest(String name, String description, float price, Grade grade, List<String> what_yow_will_learn, LevelOfDifficulty difficulty_level, Long teacherId, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.grade = grade;
        this.what_yow_will_learn = what_yow_will_learn;
        this.difficulty_level = difficulty_level;
        this.teacherId = teacherId;
        this.category = category;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<String> getWhat_yow_will_learn() {
        return what_yow_will_learn;
    }

    public void setWhat_yow_will_learn(List<String> what_yow_will_learn) {
        this.what_yow_will_learn = what_yow_will_learn;
    }

    public LevelOfDifficulty getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(LevelOfDifficulty difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        teacherId = teacherId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
