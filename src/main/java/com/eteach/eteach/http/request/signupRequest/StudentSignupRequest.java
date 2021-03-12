package com.eteach.eteach.http.request.signupRequest;

public class StudentSignupRequest extends SignUpRequest{

    private String address;
    private String grade;

    public StudentSignupRequest(){}

    public StudentSignupRequest(String address, String grade){
        this.address = address;
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
