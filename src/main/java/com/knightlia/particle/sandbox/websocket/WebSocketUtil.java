package com.knightlia.particle.sandbox.websocket;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knightlia.particle.sandbox.util.SpringContext;

public class WebSocketUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = SpringContext.getBean(ObjectMapper.class);

    public static void sendSinglePayload(WebSocketSession session, Object message) {
        sendSinglePayload(session, createTextMessage(message));
    }

    public static void broadcast(Collection<WebSocketSession> sessions, Object message) {
        final TextMessage textMessage = createTextMessage(message);
        sessions.stream()
            .filter(WebSocketSession::isOpen)
            .forEach(session -> sendSinglePayload(session, textMessage));
    }

    private static void sendSinglePayload(WebSocketSession session, TextMessage message) {
        try {
            LOG.info("Sending WebSocket payload: session={}, sessionID={}, message={}",
                session.getRemoteAddress(), session.getId(), message);
            session.sendMessage(message);
        } catch (IOException e) {
            LOG.error("Failed to send WebSocket payload: session={}, sessionID={}, message={}",
                session.getRemoteAddress(), session.getId(), message, e);
        }
    }

    private static TextMessage createTextMessage(Object message) {
        try {
            if (message instanceof String) {
                return new TextMessage((String) message);
            }
            return new TextMessage(OBJECT_MAPPER.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            LOG.error("Failed to serialise message to a JSON string: message={}", message, e);
            return null;
        }
    }
}
