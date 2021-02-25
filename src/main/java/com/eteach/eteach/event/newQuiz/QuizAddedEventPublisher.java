package com.eteach.eteach.event.newQuiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class QuizAddedEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public QuizAddedEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishQuizAddedEvent(QuizAddedEvent quizAddedEvent){
        publisher.publishEvent(quizAddedEvent);
    }
}
