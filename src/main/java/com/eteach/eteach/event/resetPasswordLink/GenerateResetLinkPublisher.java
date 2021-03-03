package com.eteach.eteach.event.resetPasswordLink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class GenerateResetLinkPublisher {
    private final ApplicationEventPublisher publisher;

    @Autowired
    public GenerateResetLinkPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishGenerateResetLinkEvent(GenerateResetLinkEvent generateResetLinkEvent){
        publisher.publishEvent(generateResetLinkEvent);
    }

}
