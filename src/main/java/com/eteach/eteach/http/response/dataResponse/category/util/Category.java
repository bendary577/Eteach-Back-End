package com.eteach.eteach.http.response.dataResponse.category.util;
import com.eteach.eteach.model.course.Course;

import java.util.List;

public class Category {

    private Long id;
    private String name;
    private String description;
    private List<Course> courses;

    public Category(){}

    public Category(Long id, String name, String description, List<Course> courses){
        this.id = id;
        this.name = name;
        this.description = description;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
