package com.knightlia.particle.sandbox;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knightlia.particle.sandbox.websocket.TokenHandler;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("TESTING")
public abstract class AbstractTestBase {

    private static final String VALID_TOKEN = "A_VALID_TOKEN";

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    protected ObjectMapper objectMapper;

    protected void setupTokens(TokenHandler tokenHandler) {
        when(tokenHandler.verifyToken(VALID_TOKEN)).thenReturn(true);
    }

    protected void clearTokens() {
        applicationEventPublisher.publishEvent(VALID_TOKEN);
    }

    protected <T> ResponseEntity<T> GET(String url, Class<T> responseType) {
        return testRestTemplate.getForEntity(buildURL(url), responseType);
    }

    protected <T, K> ResponseEntity<T> POST(String url, K body, HttpHeaders httpHeaders, Class<T> responseType) {
        HttpEntity<K> httpEntity = new HttpEntity<>(body, httpHeaders);
        return testRestTemplate.exchange(buildURL(url), POST, httpEntity, responseType);
    }

    protected HttpHeaders headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        httpHeaders.set("ST", VALID_TOKEN);
        return httpHeaders;
    }

    protected <T> void assertObjects(T expected, T actual) {
        try {
            JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected), objectMapper.writeValueAsString(actual), false);
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String buildURL(String path) {
        return "http://localhost:" + port + path;
    }
}
