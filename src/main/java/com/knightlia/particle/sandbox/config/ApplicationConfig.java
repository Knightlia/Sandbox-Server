package com.knightlia.particle.sandbox.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knightlia.particle.sandbox.interceptor.TokenInterceptor;
import com.knightlia.particle.sandbox.util.validation.Validator;
import com.knightlia.particle.sandbox.websocket.MessageEventListener;
import com.knightlia.particle.sandbox.websocket.TokenHandler;
import com.knightlia.particle.sandbox.websocket.WebSocketHandler;

@Configuration
public class ApplicationConfig {

    @Bean
    public Validator validator() {
        return new Validator();
    }

    @Bean
    public TokenHandler tokenHandler(ApplicationEventPublisher applicationEventPublisher) {
        return new TokenHandler(applicationEventPublisher);
    }

    @Bean
    public TokenInterceptor tokenInterceptor(TokenHandler tokenHandler, ObjectMapper objectMapper) {
        return new TokenInterceptor(tokenHandler, objectMapper);
    }

    @Bean
    public WebSocketHandler webSocketHandler(TokenHandler tokenHandler, ApplicationEventPublisher applicationEventPublisher) {
        return new WebSocketHandler(tokenHandler, applicationEventPublisher);
    }

    @Bean
    public MessageEventListener eventListener(TokenHandler tokenHandler) {
        return new MessageEventListener(tokenHandler);
    }
}
