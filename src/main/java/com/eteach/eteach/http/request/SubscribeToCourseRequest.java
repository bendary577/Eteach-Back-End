package com.eteach.eteach.http.request;

import com.eteach.eteach.model.course.Category;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
