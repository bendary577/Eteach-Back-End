package com.eteach.eteach.http.request.signupRequest;

import com.eteach.eteach.model.course.Category;

public class TeacherSignupRequest extends SignUpRequest{
    private Category subject;
    private String facebook_link;
    private String twitter_link;

    public TeacherSignupRequest(){}

    public TeacherSignupRequest(Category subject,
                                String facebook_link,
                                String twitter_link){
        this.subject = subject;
        this.facebook_link = facebook_link;
        this.twitter_link = twitter_link;
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
