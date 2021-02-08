package com.knightlia.particle.sandbox.model.websocket;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knightlia.particle.sandbox.model.event.UserListEvent;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserListPayload {

    @JsonProperty("mt")
    private MessageType messageType;

    @JsonProperty("ul")
    private Collection<String> userList;

    public static UserListPayload userListPayloadFromEvent(UserListEvent userListEvent) {
        return UserListPayload.builder()
            .messageType(MessageType.USER_LIST_PAYLOAD)
            .userList(userListEvent.getUserList())
            .build();
    }
}
