package com.eteach.eteach.http.request.dataRequest.course;

public class AddSectionRequest {

    private String title;

    public AddSectionRequest(){}

    public AddSectionRequest(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
