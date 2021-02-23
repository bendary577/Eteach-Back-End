package com.eteach.eteach.model;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image extends File{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, updatable = false)
    private String id;

    @OneToOne(mappedBy = "thumbnail")
    private Course course;

    public Image() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
