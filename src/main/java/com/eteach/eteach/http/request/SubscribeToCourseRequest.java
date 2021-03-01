package com.eteach.eteach.http.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class SubscribeToCourseRequest implements Serializable {
    @NotBlank
    private Long studentId;

    @NotBlank
    private Long courseId;

    public SubscribeToCourseRequest(){}

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
