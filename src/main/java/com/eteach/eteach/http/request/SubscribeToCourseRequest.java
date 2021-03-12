package com.eteach.eteach.http.request;

import com.eteach.eteach.model.course.Category;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class SubscribeToCourseRequest implements Serializable {

    private Long studentId;

    public SubscribeToCourseRequest(){}

    public SubscribeToCourseRequest(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public static class SignUpRequest implements Serializable {

        private static final long serialVersionUID = -8091879091924046844L;

        @NotBlank
        @Size(min = 3, max = 15)
        private String username;

        @NotBlank
        @Size(max = 40)
        @Email
        private String email;

        @NotBlank
        @Size(min = 6, max = 20)
        private String password;

        @NotBlank
        @Size(min = 6, max = 20)
        private String phone_number;

        @NotNull
        private int accountType;

        //student
        private String address;
        private String grade;
        //teacher
        private Category subject;
        private String facebook_link;
        private String twitter_link;

        public SignUpRequest(){}

        public SignUpRequest(@NotBlank @Size(min = 3, max = 15) String username,
                             @NotBlank @Size(max = 40) @Email String email,
                             @NotBlank @Size(min = 6, max = 20) String password,
                             @NotBlank @Size(min = 6, max = 20) String phone_number,
                             @NotNull int accountType,
                             String address,
                             String grade,
                             Category subject,
                             String facebook_link,
                             String twitter_link) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.phone_number = phone_number;
            this.accountType = accountType;
            this.grade = grade;
            this.address = address;
            this.subject = subject;
            this.facebook_link = facebook_link;
            this.twitter_link = twitter_link;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public Category getSubject() {
            return subject;
        }

        public void setSubject(Category subject) {
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
}
