package com.eteach.eteach.http.request.dataRequest.course;

import java.io.Serializable;

public class CourseRegistrationRequest implements Serializable {

    public Long studentId;

    public CourseRegistrationRequest(){}

    public CourseRegistrationRequest(Long studentId){
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
