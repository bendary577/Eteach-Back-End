package com.eteach.eteach.model.account;

import com.eteach.eteach.config.file.UserDataConfig;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.notification.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="account")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Column(length = 100)
    private String about_description;

    @OneToOne(mappedBy = "account")
    @JsonIgnoreProperties("account")
    protected User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    protected Image image;

    @OneToMany(mappedBy="account",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    protected List<Notification> notifications;

    private transient UserDataConfig userDataConfig = null;

    @Autowired
    public Account(UserDataConfig userDataConfig) {
        this.userDataConfig = userDataConfig;
    }

    public Account(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getAbout_description() {
        return about_description;
    }

    public void setAbout_description(String about_description) {
        this.about_description = about_description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Transient
    public String getImagePath() {
        String path = new StringBuilder(userDataConfig.getAccountsDirectory())
                .append(java.io.File.separator)
                .append(this.id)
                .append(java.io.File.separator)
                .append("user_photos")
                .append(java.io.File.separator).toString();
        System.out.println("account image path is " + path );
        return path;
    }
}
