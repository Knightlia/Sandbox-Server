package com.knightlia.particle.sandbox.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.knightlia.particle.sandbox.AbstractTestBase;
import com.knightlia.particle.sandbox.model.request.NicknameRequest;
import com.knightlia.particle.sandbox.model.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TokenInterceptorTest extends AbstractTestBase {

    // An endpoint that requires a session token
    private static final String URL = "/v1/nickname";

    @Test
    public void invalidTokenReturnsFalseTest() {
        final NicknameRequest nicknameRequest = new NicknameRequest();
        nicknameRequest.setNickname("Foo");

        ResponseEntity<Response> responseEntity = POST(URL, nicknameRequest, headers(), Response.class);

        final Response expected = Response.builder()
            .status(false)
            .message("Non-existent or invalid token specified.")
            .build();

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(expected, responseEntity.getBody());
    }
}
