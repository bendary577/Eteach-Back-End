package com.eteach.eteach.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="notifications")
public class Notification {

    private String message;

    public Notification(){}

    public Notification (String content) {
        this.message = content;
    }

    public String getMessage() {
        return message;
    }
}
