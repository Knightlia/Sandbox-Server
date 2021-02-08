package com.knightlia.particle.sandbox.websocket;

import org.springframework.context.event.EventListener;

import com.knightlia.particle.sandbox.model.event.UserListEvent;
import com.knightlia.particle.sandbox.model.request.MessageRequest;
import com.knightlia.particle.sandbox.model.websocket.MessagePayload;
import com.knightlia.particle.sandbox.model.websocket.UserListPayload;

import static com.knightlia.particle.sandbox.model.websocket.MessagePayload.messagePayloadFromRequest;
import static com.knightlia.particle.sandbox.model.websocket.UserListPayload.userListPayloadFromEvent;
import static com.knightlia.particle.sandbox.websocket.WebSocketUtil.broadcast;

public class MessageEventListener {

    private final TokenHandler tokenHandler;

    public MessageEventListener(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @EventListener
    public void onUserListEvent(UserListEvent userListEvent) {
        UserListPayload userListPayload = userListPayloadFromEvent(userListEvent);
        broadcast(tokenHandler.getSessionList(), userListPayload);
    }

    @EventListener
    public void onMessageRequest(MessageRequest messageRequest) {
        MessagePayload messagePayload = messagePayloadFromRequest(messageRequest);
        broadcast(tokenHandler.getSessionList(), messagePayload);
    }
}
