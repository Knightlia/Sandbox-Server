package com.knightlia.particle.sandbox.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knightlia.particle.sandbox.model.RequiresToken;
import com.knightlia.particle.sandbox.model.request.MessageRequest;
import com.knightlia.particle.sandbox.model.response.Response;
import com.knightlia.particle.sandbox.service.MessageService;
import com.knightlia.particle.sandbox.util.validation.Validator;

@RestController
@RequestMapping("/v1/message")
public class MessageController {

    private final MessageService messageService;
    private final Validator validator;

    public MessageController(MessageService messageService, Validator validator) {
        this.messageService = messageService;
        this.validator = validator;
    }

    @PostMapping
    @RequiresToken
    public Response newMessage(@RequestHeader("ST") String token, @RequestBody MessageRequest messageRequest) {
        final String message = validator.validateMessageRequest(messageRequest);
        if (message == null) {
            messageService.handleNewMessage(token, messageRequest);
            return null; // TODO: Return some kind of reference (used for validating messages being sent)
        }

        return Response.builder()
            .status(false)
            .message(message)
            .build();
    }
}
