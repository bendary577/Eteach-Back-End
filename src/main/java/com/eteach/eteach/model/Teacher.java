package com.eteach.eteach.model;

import com.eteach.eteach.enums.Subjects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="teacher")
@EntityListeners(AuditingEntityListener.class)
public class Teacher implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String first_name;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String second_name;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 11)
    private String phone_number;

    @Column(nullable = false, length = 100)
    private String about_description;

    @Column(nullable = false, length = 50)
    private String image;

    @Column(nullable = false, length = 100)
    private Subjects subject;

    @Column(nullable = false, length = 100)
    private String facebook_link;

    @Column(nullable = false, length = 100)
    private String twitter_link;

    @OneToMany(mappedBy="teacher",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Course> courses;

    public Teacher() { }

    public Teacher(@JsonProperty("id")Long id, @JsonProperty("first_name") String first_name,
                   @JsonProperty("second_name") String second_name, @JsonProperty("email") String email,
                   @JsonProperty("password") String password, @JsonProperty("phone_number") String phone_number,
                   @JsonProperty("address") String about_description, @JsonProperty("grade")String image,
                   @JsonProperty("image") Subjects subject, @JsonProperty("facebook_link") String facebook_link,
                   @JsonProperty("twitter_link") String twitter_link){
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
