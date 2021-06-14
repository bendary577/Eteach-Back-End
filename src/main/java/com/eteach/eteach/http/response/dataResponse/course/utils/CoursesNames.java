package com.eteach.eteach.http.response.dataResponse.course.utils;

public class CoursesNames {

    private Long id;
    private String name;

    public CoursesNames(){}

    public CoursesNames(Long id, String name){
        this.id = id;
        this.name = name;
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
}
