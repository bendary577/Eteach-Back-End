package com.eteach.eteach.http.request;

import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCourseRequest {

    private String name;
    private String description;
    private float price;
    private String duration;
    private String intro;
    private Grade grade;
    private String what_yow_will_learn;
    private LevelOfDifficulty difficulty_level;
    private Long teacherId;
    private Long categoryId;

    public AddCourseRequest(){}
    public AddCourseRequest(String name, String description, float price, String duration, String intro, Grade grade, String what_yow_will_learn, LevelOfDifficulty difficulty_level, Long teacherId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.intro = intro;
        this.grade = grade;
        this.what_yow_will_learn = what_yow_will_learn;
        this.difficulty_level = difficulty_level;
        this.teacherId = teacherId;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getWhat_yow_will_learn() {
        return what_yow_will_learn;
    }

    public void setWhat_yow_will_learn(String what_yow_will_learn) {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
