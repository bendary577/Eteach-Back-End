package com.eteach.eteach.http.response.profileResponse;

import com.eteach.eteach.enums.AccountType;
import com.eteach.eteach.http.response.ApiResponse;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;

public class UserProfileResponse extends ApiResponse {

    private String username;
    private String about;
    private String imagePath;
    private String accountType;

    public UserProfileResponse(){}

    public UserProfileResponse(HttpStatus status,
                               String message,
                               String username,
                               String about,
                               String imagePath,
                               String accountType){
        super(status, message);
        this.username = username;
        this.about = about;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
