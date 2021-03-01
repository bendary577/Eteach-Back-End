package com.eteach.eteach.api.notification;

import com.eteach.eteach.http.request.LoginRequest;
import com.eteach.eteach.model.quiz.Quiz;
import com.eteach.eteach.notification.Notification;
import com.eteach.eteach.notification.NotificationService;
import com.eteach.eteach.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    NotificationService notificationService;
    QuizService quizService;

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  QuizService quizService){
        this.notificationService = notificationService;
        this.quizService = quizService;
    }

    //------------------------------- NEW QUIZ ADDED NOTIFICATION ----------------------------
    @MessageMapping("/new-quiz/{quizId}")            //message is sent from 'app/new-quiz'
    @SendTo("/topic/new-quiz")                      //message is sent to all topic subscribers
    public ResponseEntity<?> newQuizAddedNotification(@DestinationVariable Long quizId) {
        //get the new added quiz
        Quiz quiz = quizService.getQuiz(quizId);
        String quizName = quiz.getTitle();
        Notification notification = new Notification("");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //------------------------------- QUIZ RESULT NOTIFICATION ----------------------------
    @MessageMapping("/quiz-result")
    @SendToUser("/queue/quiz-result-finished")
    public ResponseEntity<?> quizResultNotification(@RequestBody LoginRequest requestInfo) {
        // Send the notification to "UserA" (by username)
        notificationService.notifyAfterQuizResult(new Notification("hello"), requestInfo.getUsername());

        //Return an http 200 status code
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //------------------------------- NEW LESSON ADDED NOTIFICATION ----------------------------
    @MessageMapping("/new-lesson")
    @SendTo("/topic/new-quiz-added")
    public ResponseEntity<?> newLessonAddedNotification(@RequestBody LoginRequest requestInfo) {
        // Send the notification to "UserA" (by username)
        notificationService.notifyAfterQuizResult(new Notification("hello"), requestInfo.getUsername());

        // Return an http 200 status code
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //------------------------------------  ----------------------------

}
