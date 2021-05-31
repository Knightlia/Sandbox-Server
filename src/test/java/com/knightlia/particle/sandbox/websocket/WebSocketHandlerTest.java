package com.knightlia.particle.sandbox.websocket;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.knightlia.particle.sandbox.AbstractTestBase;
import com.knightlia.particle.sandbox.WebSocketTestClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;

public class WebSocketHandlerTest extends AbstractTestBase {

    private static final WebSocketTestClient WS = new WebSocketTestClient();

    @Test
    public void sendTokenAfterWebSocketClientConnects() throws Exception {
        setupWebSocket(1);
        assertThat(WS.messages, hasSize(1));
    }

    @AfterEach
    public void teardown() throws IOException {
        WS.closeSession();
    }

    public void setupWebSocket(int count) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        WS.connect("ws://localhost:" + port + "/v1/streamer", countDownLatch);
        boolean result = countDownLatch.await(4, TimeUnit.SECONDS);
        if (!result) fail("Countdown latch timed out.");
    }
}
