package com.eteach.eteach.model;


import com.eteach.eteach.enums.Grade;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Entity
@Table(name="student_account")
@EntityListeners(AuditingEntityListener.class)
public class StudentAccount implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 50)
    private Grade grade;

    @Column(nullable = false, length = 100)
    private String image;

    public StudentAccount() { }

    public StudentAccount(@JsonProperty("id")Long id, @JsonProperty("address") String address,
                          @JsonProperty("grade")Grade grade, @JsonProperty("image") String image){
        this.id = id;
        this.address = address;
        this.grade = grade;
        this.image = image;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Transient
    public String getImagePath() {
        if (image == null || id == null) return null;

        return "/user-photos/" + id + "/" + image;
    }
}
