package com.knightlia.particle.sandbox.model.response;

import java.util.Collection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NicknameResponse {
    private final boolean status;
    private final String message, nickname;
    private final Collection<String> userList;
}
