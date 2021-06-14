package com.eteach.eteach.http.response.dataResponse.teacher.utils;

public class TeacherNameWithId {

    private String name;
    private Long id;

    public TeacherNameWithId(){}

    public TeacherNameWithId(String name, Long id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
