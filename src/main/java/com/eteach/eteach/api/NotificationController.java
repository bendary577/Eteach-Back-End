package com.eteach.eteach.api;


import com.eteach.eteach.http.LoginRequest;
import com.eteach.eteach.model.Notification;
import com.eteach.eteach.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @MessageMapping("/secured/room")     //message is sent from
    @SendTo("/topic/greetings")         //message is sent to
    public ResponseEntity<?> someAction(@RequestBody LoginRequest requestInfo) {
        // Send the notification to "UserA" (by username)
        notificationService.notify(new Notification("hello"), requestInfo.getUsername());

        // Return an http 200 status code
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
