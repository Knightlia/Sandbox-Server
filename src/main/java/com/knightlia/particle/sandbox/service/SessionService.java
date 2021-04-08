package com.knightlia.particle.sandbox.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.knightlia.particle.sandbox.model.event.UserListEvent;
import com.knightlia.particle.sandbox.model.event.UserListPublishEvent;
import com.knightlia.particle.sandbox.model.response.NicknameResponse;

@Service
public class SessionService {

    private final ConcurrentMap<String, String> userSessionMap = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher applicationEventPublisher;

    public SessionService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public NicknameResponse setNickname(String sessionID, String nickname) {
        if (userSessionMap.containsValue(nickname)) {
            return NicknameResponse.builder()
                .status(false)
                .message("Nickname " + nickname + " is already taken.")
                .build();
        }

        userSessionMap.put(sessionID, nickname);
        publishUserList();

        return NicknameResponse.builder()
            .status(true)
            .nickname(nickname)
            .userList(userSessionMap.values())
            .build();
    }

    public boolean validUserSession(String token, String name) {
        return userSessionMap.get(token).equals(name);
    }

    @EventListener
    public void removeSession(String token) {
        userSessionMap.remove(token);
    }

    @EventListener(UserListPublishEvent.class)
    public void publishUserList() {
        applicationEventPublisher.publishEvent(UserListEvent.builder()
            .userList(userSessionMap.values())
            .build());
    }
}
