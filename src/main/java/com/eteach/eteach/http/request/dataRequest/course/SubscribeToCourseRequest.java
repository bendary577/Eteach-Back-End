package com.eteach.eteach.http.request.dataRequest.course;

import java.io.Serializable;

public class SubscribeToCourseRequest implements Serializable {

    private Long studentId;
    private String courseCode;

    public SubscribeToCourseRequest(){}

    public SubscribeToCourseRequest(Long studentId,
                                    String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
