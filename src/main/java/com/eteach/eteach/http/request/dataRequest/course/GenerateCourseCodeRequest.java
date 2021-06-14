package com.eteach.eteach.http.request.dataRequest.course;

import java.io.Serializable;

public class GenerateCourseCodeRequest implements Serializable {

    private Long studentId;

    public GenerateCourseCodeRequest(){}

    public GenerateCourseCodeRequest(Long studentId){
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
