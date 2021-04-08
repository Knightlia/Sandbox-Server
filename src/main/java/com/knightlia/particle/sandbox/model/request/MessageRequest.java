package com.knightlia.particle.sandbox.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private String sender, message, id, avatarColour;
    private long time;
}
