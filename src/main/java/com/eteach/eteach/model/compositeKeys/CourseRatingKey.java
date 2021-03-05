package com.eteach.eteach.model.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CourseRatingKey implements Serializable{
    @Column(name = "student_id")
    Long studentId;

    @Column(name = "course_id")
    Long courseId;

    public CourseRatingKey(){}


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
        courseId = courseId;
    }
}
