package com.eteach.eteach.service;

import com.eteach.eteach.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Spring will automatically prepend "/user/" to destination => "/user/notify"
    public void notify(Notification notification, String username) {
        messagingTemplate.convertAndSendToUser(username, "/notify", notification);
    }

}
