package com.knightlia.particle.sandbox.websocket;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.WebSocketSession;

public class TokenHandler {

    private final ConcurrentMap<WebSocketSession, String> sessionTokenMap = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher applicationEventPublisher;

    public TokenHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void addSessionToken(WebSocketSession session, String token) {
        sessionTokenMap.put(session, token);
    }

    public boolean verifyToken(String token) {
        return sessionTokenMap.containsValue(token);
    }

    public void removeSessionToken(WebSocketSession session) {
        String token = sessionTokenMap.remove(session);
        applicationEventPublisher.publishEvent(token);
    }

    public Collection<WebSocketSession> getSessionList() {
        return sessionTokenMap.keySet();
    }
}
