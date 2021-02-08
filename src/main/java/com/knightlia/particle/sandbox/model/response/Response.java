package com.knightlia.particle.sandbox.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {
    protected boolean status;
    protected String message;
}
