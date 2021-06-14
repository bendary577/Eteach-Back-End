package com.eteach.eteach.http.response.profileResponse;

import com.eteach.eteach.model.course.Category;
import org.springframework.http.HttpStatus;

public class TeacherProfileResponse extends UserProfileResponse{

    private String facebook_link;
    private String twitter_link;
    private Category subject;

    public TeacherProfileResponse(){}

    public TeacherProfileResponse(HttpStatus status,
                                  String message,
                                  Long id,
                                  String username,
                                  String about,
                                  byte[] image,
                                  String accountType,
                                  String facebook_link,
                                  String twitter_link,
                                  Category subject){
        super(status, message,id, username, about, image, accountType);
        this.facebook_link = facebook_link;
        this.twitter_link = twitter_link;
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

    public Category getSubject() {
        return subject;
    }

    public void setSubject(Category subject) {
        this.subject = subject;
    }
}
