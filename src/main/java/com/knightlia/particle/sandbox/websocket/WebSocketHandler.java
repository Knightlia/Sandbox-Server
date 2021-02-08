package com.knightlia.particle.sandbox.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.knightlia.particle.sandbox.model.websocket.MessageType;
import com.knightlia.particle.sandbox.model.websocket.TokenPayload;

import static com.knightlia.particle.sandbox.websocket.WebSocketUtil.sendSinglePayload;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);
    private final TokenHandler tokenHandler;

    public WebSocketHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOG.info("New WebSocket client: session={}, id={}", session.getRemoteAddress(), session.getId());
        tokenHandler.addSessionToken(session, session.getId());
        sendSinglePayload(session, TokenPayload.builder()
            .messageType(MessageType.TOKEN_PAYLOAD)
            .token(session.getId())
            .build());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LOG.info("WebSocket client connection closed: session={}, id={}, status={}, reason={}",
            session.getRemoteAddress(), session.getId(), status.getCode(), status.getReason());
        tokenHandler.removeSessionToken(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) {
        LOG.error("Error with WebSocket client: session={}, id={}, message={}",
            session.getRemoteAddress(), session.getId(), e.getMessage(), e);
        tokenHandler.removeSessionToken(session);
    }
}
