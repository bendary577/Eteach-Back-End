package com.eteach.eteach.model;


import com.eteach.eteach.enums.Grade;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Entity
@Table(name="student")
@EntityListeners(AuditingEntityListener.class)
public class Student implements Serializable {
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
    private String address;

    @Column(nullable = false, length = 50)
    private Grade grade;

    @Column(nullable = false, length = 100)
    private String image;

    public Student() { }

    public Student(@JsonProperty("id")Long id, @JsonProperty("first_name") String first_name,
                   @JsonProperty("second_name") String second_name, @JsonProperty("email") String email,
                   @JsonProperty("password") String password, @JsonProperty("phone_number") String phone_number,
                   @JsonProperty("address") String address, @JsonProperty("grade")Grade grade,
                   @JsonProperty("image") String image){
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
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
