package com.eteach.eteach.http.request;

import java.io.Serializable;

public class SubscribeToCourseRequest implements Serializable {

    private Long studentId;

    public SubscribeToCourseRequest(){}

    public SubscribeToCourseRequest(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
