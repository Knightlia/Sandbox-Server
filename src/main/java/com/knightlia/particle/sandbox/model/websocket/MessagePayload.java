package com.knightlia.particle.sandbox.model.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knightlia.particle.sandbox.model.request.MessageRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessagePayload {

    @JsonProperty("mt")
    private MessageType messageType;

    @JsonProperty("s")
    private String sender;

    @JsonProperty("m")
    private String message;

    @JsonProperty("t")
    private long time;

    public static MessagePayload messagePayloadFromRequest(MessageRequest messageRequest) {
        return MessagePayload.builder()
            .messageType(MessageType.MESSAGE_PAYLOAD)
            .sender(messageRequest.getSender())
            .message(messageRequest.getMessage())
            .time(messageRequest.getTime())
            .build();
    }
}
