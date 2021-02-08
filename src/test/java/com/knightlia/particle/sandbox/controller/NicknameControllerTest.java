package com.knightlia.particle.sandbox.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.knightlia.particle.sandbox.AbstractTestBase;
import com.knightlia.particle.sandbox.model.request.NicknameRequest;
import com.knightlia.particle.sandbox.model.response.NicknameResponse;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class NicknameControllerTest extends AbstractTestBase {

    private static final String URL = "/v1/nickname";

    @BeforeEach
    public void setup() {
        setupTokens();
    }

    @Test
    public void nullNameReturnsFalseTest() {
        ResponseEntity<NicknameResponse> responseEntity = POST(
            URL, createRequest(null), headers(), NicknameResponse.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(response(false, "Nickname should not be null.", null, null), responseEntity.getBody());
    }

    @Test
    public void tooLongNameReturnsFalseTest() {
        ResponseEntity<NicknameResponse> responseEntity = POST(
            URL,
            createRequest("ThisNameHasAroundAHundredCharactersBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlahBlah"),
            headers(),
            NicknameResponse.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(response(
            false,
            "Nickname should not have more than 100 characters.",
            null,
            null), responseEntity.getBody());
    }

    @Test
    public void validNameReturnsTrueTest() {
        ResponseEntity<NicknameResponse> responseEntity = POST(
            URL, createRequest("Foo"), headers(), NicknameResponse.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(response(
            true,
            null,
            "Foo",
            new String[]{"Foo"}), responseEntity.getBody());
    }

    @Test
    public void sameTokenNameReplacedTest() {
        POST(URL, createRequest("Foo"), headers(), NicknameResponse.class);
        ResponseEntity<NicknameResponse> responseEntity = POST(
            URL, createRequest("Foo2"), headers(), NicknameResponse.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(response(
            true,
            null,
            "Foo2",
            new String[]{"Foo2"}), responseEntity.getBody());
    }

    @Test
    public void multipleUsersReturnsCorrectListTest() {
        when(tokenHandler.verifyToken("another_valid_token")).thenReturn(true);

        POST(URL, createRequest("Foo"), headers(), NicknameResponse.class);

        HttpHeaders httpHeaders = headers();
        httpHeaders.set("ST", "another_valid_token");
        ResponseEntity<NicknameResponse> responseEntity = POST(
            URL, createRequest("Foo2"), httpHeaders, NicknameResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(response(
            true,
            null,
            "Foo2",
            new String[]{"Foo", "Foo2"}), responseEntity.getBody());

        applicationEventPublisher.publishEvent("another_valid_token");
    }

    @Test
    public void takenNameIsRejectedTest() {
        when(tokenHandler.verifyToken("another_valid_token")).thenReturn(true);

        POST(URL, createRequest("Foo"), headers(), NicknameResponse.class);

        HttpHeaders httpHeaders = headers();
        httpHeaders.set("ST", "another_valid_token");
        POST(URL, createRequest("Foo"), httpHeaders, NicknameResponse.class);

        ResponseEntity<NicknameResponse> responseEntity = POST(
            URL, createRequest("Foo"), httpHeaders, NicknameResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertObjects(response(
            false,
            "Nickname Foo is already taken.",
            null,
            null), responseEntity.getBody());

        applicationEventPublisher.publishEvent("another_valid_token");
    }

    @AfterEach
    public void afterEach() {
        clearTokens();
    }

    private NicknameRequest createRequest(String nickname) {
        final NicknameRequest nicknameRequest = new NicknameRequest();
        nicknameRequest.setNickname(nickname);
        return nicknameRequest;
    }

    private NicknameResponse response(boolean status, String message, String name, String[] userList) {
        return NicknameResponse.builder()
            .status(status)
            .message(message)
            .nickname(name)
            .userList(userList == null ? null : asList(userList))
            .build();
    }
}
