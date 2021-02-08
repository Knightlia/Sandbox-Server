package com.knightlia.particle.sandbox.model.event;

import java.util.Collection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserListEvent {
    private final Collection<String> userList;
}
