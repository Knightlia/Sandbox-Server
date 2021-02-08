package com.knightlia.particle.sandbox.model.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenPayload {

    @JsonProperty("mt")
    private MessageType messageType;

    @JsonProperty("t")
    private String token;
}
