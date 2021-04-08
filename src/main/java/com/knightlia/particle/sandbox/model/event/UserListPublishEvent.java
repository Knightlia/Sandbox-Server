package com.knightlia.particle.sandbox.model.event;

import org.springframework.context.ApplicationEvent;

public class UserListPublishEvent extends ApplicationEvent {
    public UserListPublishEvent(Object source) {
        super(source);
    }
}
