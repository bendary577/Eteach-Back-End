package com.eteach.eteach.model;

import com.eteach.eteach.enums.Subjects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="teacher_account")
@EntityListeners(AuditingEntityListener.class)
public class TeacherAccount implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String about_description;

    @Column(nullable = false, length = 50)
    private String image;

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.ORDINAL)
    private Subjects subject;

    @Column(nullable = false, length = 100)
    private String facebook_link;

    @Column(nullable = false, length = 100)
    private String twitter_link;

    @OneToMany(mappedBy="teacher_account",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Course> courses;

    public TeacherAccount() { }

    public TeacherAccount(@JsonProperty("id")Long id, @JsonProperty("address") String about_description,
                          @JsonProperty("grade")String image, @JsonProperty("image") Subjects subject,
                          @JsonProperty("facebook_link") String facebook_link, @JsonProperty("twitter_link") String twitter_link){
        this.id = id;
        this.about_description = about_description;
        this.image = image;
        this.subject = subject;
        this.facebook_link = facebook_link;
        this.twitter_link = twitter_link;

    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbout_description() {
        return about_description;
    }

    public void setAbout_description(String about_description) {
        this.about_description = about_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getTwitter_link() {
        return twitter_link;
    }

    public void setTwitter_link(String twitter_link) {
        this.twitter_link = twitter_link;
    }
}