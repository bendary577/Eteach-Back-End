package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="videos")
public class Video extends File{

    @JsonIgnore
    @Column
    private String thumbnail_path;

    @JsonIgnore
    @Column
    private boolean thumbnail;

    @OneToOne(mappedBy = "trailer_video")
    private Course course;

    @OneToOne(mappedBy = "video")
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
