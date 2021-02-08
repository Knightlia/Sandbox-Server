package com.knightlia.particle.sandbox.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.knightlia.particle.sandbox.AbstractTestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StatusControllerTest extends AbstractTestBase {

    @Value("${sandbox.version}")
    private String version;

    @Test
    public void versionHealthCheckTest() {
        ResponseEntity<String> responseEntity = GET("/", String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is("Sandbox Server - " + version));
    }
}
