package com.eteach.eteach.notification;

import com.eteach.eteach.model.account.Account;
import com.eteach.eteach.model.account.User;
import com.eteach.eteach.security.userdetails.RealApplicationUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {


    private SimpMessagingTemplate messagingTemplate;
    private NotificationDAO notificationDAO;
    private RealApplicationUserDao userDao;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate,
                               NotificationDAO notificationDAO,
                               RealApplicationUserDao userDao){
        this.messagingTemplate = messagingTemplate;
        this.notificationDAO = notificationDAO;
        this.userDao = userDao;
    }

    //------------------------------- NOTIFY AFTER NEW QUIZ ADDED ---------------------------
    // Spring will automatically prepend "/user/" to destination => "/user/notify"
    public void notifyAfterNewQuizAdded(Notification notification, String username) {
        Optional<User> applicationUser = userDao.findByUsername(username);
        if(applicationUser.isPresent()){
            User user = applicationUser.get();
            Account account = user.getAccount();
            account.getNotifications().add(notification);
        }
        messagingTemplate.convertAndSendToUser(username, "/topic/new-quiz-added", notification);
    }

    //------------------------------- NOTIFY AFTER QUIZ RESULTS FINISHED ---------------------------
    public void notifyAfterQuizResult(Notification notification, String username) {
        Optional<User> user = userDao.findByUsername(username);
        messagingTemplate.convertAndSendToUser(username, "/queue/quiz-result-finished", notification);
    }

    //------------------------------- NOTIFY AFTER NEW LESSON ADDED ---------------------------

}
