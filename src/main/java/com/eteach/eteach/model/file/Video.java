package com.eteach.eteach.model.file;

import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.course.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="videos")
@JsonIgnoreProperties({"course", "lesson"})
public class Video extends File{

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String thumbnail_path;

    @JsonIgnore
    @Column
    private boolean thumbnail;

    @OneToOne(mappedBy = "trailer_video")
    @JsonProperty("course")
    private Course course;

    @OneToOne(mappedBy = "video")
    @JsonProperty("lesson")
    private Lesson lesson;

    public Video() {
    }

    public Video(String name, String type) {
        super(name, type);
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public boolean isThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
