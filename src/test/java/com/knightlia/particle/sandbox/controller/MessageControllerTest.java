package com.knightlia.particle.sandbox.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.knightlia.particle.sandbox.AbstractTestBase;
import com.knightlia.particle.sandbox.model.request.MessageRequest;
import com.knightlia.particle.sandbox.model.request.NicknameRequest;
import com.knightlia.particle.sandbox.model.response.NicknameResponse;
import com.knightlia.particle.sandbox.model.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MessageControllerTest extends AbstractTestBase {

    private static final String URL = "/v1/message";

    @BeforeEach
    public void setup() {
        setupTokens();
        POST("/v1/nickname", nicknameRequest(), headers(), NicknameResponse.class);
    }

    @Test
    public void canSendMessageTest() {
        ResponseEntity<Response> responseEntity = POST(URL, messageRequest(), headers(), Response.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        // TODO: Return success response
    }

    @Test
    public void messageRequestFailsWithInvalidSenderTest() {
        MessageRequest messageRequest = messageRequest();
        messageRequest.setSender(null);

        ResponseEntity<Response> responseEntity = POST(URL, messageRequest, headers(), Response.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(rejectionResponse("Sender should not be null."), responseEntity.getBody());
    }

    @Test
    public void messageRequestFailsWithInvalidTimeTest() {
        MessageRequest messageRequest = messageRequest();
        messageRequest.setTime(-123);

        ResponseEntity<Response> responseEntity = POST(URL, messageRequest, headers(), Response.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(rejectionResponse("Time should be greater than 0."), responseEntity.getBody());
    }

    @AfterEach
    public void afterEach() {
        clearTokens();
    }

    private NicknameRequest nicknameRequest() {
        NicknameRequest nicknameRequest = new NicknameRequest();
        nicknameRequest.setNickname("Foo");
        return nicknameRequest;
    }

    private MessageRequest messageRequest() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setSender("Foo");
        messageRequest.setMessage("This is a message.");
        messageRequest.setTime(123456789);
        return messageRequest;
    }

    private Response rejectionResponse(String message) {
        return Response.builder()
            .status(false)
            .message(message)
            .build();
    }
}
