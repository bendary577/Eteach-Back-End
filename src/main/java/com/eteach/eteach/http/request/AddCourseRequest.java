package com.eteach.eteach.http.request;

import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;

import java.util.List;

public class AddCourseRequest {

    private String name;
    private String description;
    private float price;
    private String grade;
    private List<String> what_yow_will_learn;
    private String difficulty_level;
    private String teacherName;
    private String category;

    public AddCourseRequest(){}

    public AddCourseRequest(String name, String description, float price, String grade, List<String> what_yow_will_learn, String difficulty_level, String teacherName, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.grade = grade;
        this.what_yow_will_learn = what_yow_will_learn;
        this.difficulty_level = difficulty_level;
        this.teacherName = teacherName;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<String> getWhat_yow_will_learn() {
        return what_yow_will_learn;
    }

    public void setWhat_yow_will_learn(List<String> what_yow_will_learn) {
        this.what_yow_will_learn = what_yow_will_learn;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        teacherName = teacherName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
