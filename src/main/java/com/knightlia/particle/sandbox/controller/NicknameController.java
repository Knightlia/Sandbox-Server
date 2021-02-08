package com.knightlia.particle.sandbox.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knightlia.particle.sandbox.model.RequiresToken;
import com.knightlia.particle.sandbox.model.request.NicknameRequest;
import com.knightlia.particle.sandbox.model.response.NicknameResponse;
import com.knightlia.particle.sandbox.service.SessionService;
import com.knightlia.particle.sandbox.util.validation.Validator;

@RestController
@RequestMapping("/v1/nickname")
public class NicknameController {

    private final SessionService sessionService;
    private final Validator validator;

    public NicknameController(SessionService sessionService, Validator validator) {
        this.sessionService = sessionService;
        this.validator = validator;
    }

    @PostMapping
    @RequiresToken
    public NicknameResponse setNickname(@RequestHeader("ST") String token, @RequestBody NicknameRequest nicknameRequest) {
        final String message = validator.validateNicknameRequest(nicknameRequest);

        if (message == null) {
            return sessionService.setNickname(token, nicknameRequest.getNickname());
        }

        return NicknameResponse.builder()
            .status(false)
            .message(message)
            .build();
    }
}
