package com.eteach.eteach.http.request.dataRequest.course;

import java.io.Serializable;

public class RatingCourseRequest implements Serializable {

    private Long studentId;
    private int rating;

    public RatingCourseRequest(){}

    public RatingCourseRequest(Long studentId, int rating){
        this.studentId = studentId;
        this.rating = rating;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
