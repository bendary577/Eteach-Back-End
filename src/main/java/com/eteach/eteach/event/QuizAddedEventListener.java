package com.eteach.eteach.event;

import org.springframework.context.ApplicationListener;

public class QuizAddedEventListener implements ApplicationListener<QuizAddedEvent> {
    @Override
    public void onApplicationEvent(QuizAddedEvent quizAddedEvent) {
        QuizAddedEvent employeeEvent = (QuizAddedEvent) quizAddedEvent;
        System.out.println("Teacher " + quizAddedEvent.getTeacherName()
                + "added a new quiz to the course : " + quizAddedEvent.getCourseName());
    }
}
