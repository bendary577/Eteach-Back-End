package com.eteach.eteach.http.response.dataResponse.course.utils;

import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.course.Course;

public class CourseRequestUtil {

    private Long studentId;
    private Long courseId;
    private Course course;
    private StudentAccount studentAccount;

    public CourseRequestUtil(){}

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public StudentAccount getStudentAccount() {
        return studentAccount;
    }

    public void setStudentAccount(StudentAccount studentAccount) {
        this.studentAccount = studentAccount;
    }
}
