package com.knightlia.particle.sandbox.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.knightlia.particle.sandbox.model.request.MessageRequest;

@Service
public class MessageService {

    private final SessionService sessionService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MessageService(SessionService sessionService, ApplicationEventPublisher applicationEventPublisher) {
        this.sessionService = sessionService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void handleNewMessage(String token, MessageRequest messageRequest) {
        if (sessionService.validUserSession(token, messageRequest.getSender())) {
            applicationEventPublisher.publishEvent(messageRequest);
        }
    }
}
