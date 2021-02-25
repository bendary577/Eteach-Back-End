package com.eteach.eteach.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //built-in message broker for subscriptions and broadcasting and
        //route messages whose destination header begins with `/topic` or `/queue` to the broker
        config.enableSimpleBroker("/topic", "/queue");

        //STOMP messages whose destination header begins with /app are routed to
        //@MessageMapping methods in @Controller classes.
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/secured/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //HTTP URL for the endpoint to which a WebSocket (or SockJS)
        //client needs to connect for the WebSocket handshake.
        registry.addEndpoint("/eteach").withSockJS();
    }
}