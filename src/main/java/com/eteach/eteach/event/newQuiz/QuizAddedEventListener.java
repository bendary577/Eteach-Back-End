package com.eteach.eteach.event.newQuiz;

import org.springframework.context.ApplicationListener;

public class QuizAddedEventListener implements ApplicationListener<QuizAddedEvent> {
    @Override
    public void onApplicationEvent(QuizAddedEvent quizAddedEvent) {
        QuizAddedEvent employeeEvent = (QuizAddedEvent) quizAddedEvent;

        //SEND A NOTIFICATION

        //PRINT IN CONSOLE
        System.out.println("Teacher " + quizAddedEvent.getTeacherName()
                + "added a new quiz to the course : " + quizAddedEvent.getCourseName());
    }
}
