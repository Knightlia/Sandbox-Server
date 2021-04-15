package com.knightlia.particle.sandbox.websocket;

import java.io.EOFException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.knightlia.particle.sandbox.model.event.UserListPublishEvent;
import com.knightlia.particle.sandbox.model.websocket.MessageType;
import com.knightlia.particle.sandbox.model.websocket.TokenPayload;

import static com.knightlia.particle.sandbox.websocket.WebSocketUtil.sendSinglePayload;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);
    private final TokenHandler tokenHandler;
    private final ApplicationEventPublisher applicationEventPublisher;

    public WebSocketHandler(TokenHandler tokenHandler, ApplicationEventPublisher applicationEventPublisher) {
        this.tokenHandler = tokenHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOG.info("New WebSocket client: id={}", session.getId());
        tokenHandler.addSessionToken(session, session.getId());
        sendSinglePayload(session, TokenPayload.builder()
            .messageType(MessageType.TOKEN_PAYLOAD)
            .token(session.getId())
            .build());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOG.info("WebSocket client connection closed: id={}, status={}, reason={}",
            session.getId(), status.getCode(), status.getReason());
        tokenHandler.removeSessionToken(session);
        applicationEventPublisher.publishEvent(new UserListPublishEvent(this));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) {
        if (e instanceof EOFException) {
            LOG.warn("Unexpected terminal with client: id={}, message={}", session.getId(), e.getMessage());
        } else {
            LOG.error("Error with WebSocket client: id={}, message={}", session.getId(), e.getMessage(), e);
        }
        tokenHandler.removeSessionToken(session);
    }
}
