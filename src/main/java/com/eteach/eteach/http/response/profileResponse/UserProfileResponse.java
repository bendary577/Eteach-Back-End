package com.eteach.eteach.http.response.profileResponse;

import com.eteach.eteach.http.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class UserProfileResponse extends ApiResponse {

    private Long id;
    private String username;
    private String about;
    private byte[] image;
    private String accountType;

    public UserProfileResponse(){}

    public UserProfileResponse(HttpStatus status,
                               String message,
                               Long id,
                               String username,
                               String about,
                               byte[] image,
                               String accountType){
        super(status, message);
        this.id = id;
        this.username = username;
        this.about = about;
        this.image = image;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getImagePath() {
        return image;
    }

    public void setImagePath(byte[] image) {
        this.image = image;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
