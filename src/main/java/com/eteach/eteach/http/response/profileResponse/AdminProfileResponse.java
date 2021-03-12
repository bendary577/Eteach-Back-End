package com.eteach.eteach.http.response.profileResponse;

import com.eteach.eteach.model.course.Category;
import org.springframework.http.HttpStatus;

public class AdminProfileResponse extends UserProfileResponse{

    public AdminProfileResponse(){}

    public AdminProfileResponse(HttpStatus status,
                                  String message,
                                  String username,
                                  String about,
                                  String imagePath,
                                  String accountType){
        super(status, message, username, about, imagePath, accountType);
    }




}
