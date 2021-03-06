package com.eteach.eteach.http.response.profileResponse;

import org.springframework.http.HttpStatus;

public class StudentProfileResponse extends UserProfileResponse{

    private String grade;
    private String address;

    public StudentProfileResponse(){}

    public StudentProfileResponse(HttpStatus status,
                                  String message,
                                  Long id,
                                  String username,
                                  String about,
                                  byte[] image,
                                  String accountType,
                                  String grade,
                                  String address){
        super(status, message,id, username, about, image, accountType);
        this.grade = grade;
        this.address = address;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
