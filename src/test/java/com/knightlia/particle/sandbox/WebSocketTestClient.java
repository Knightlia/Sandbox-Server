package com.knightlia.particle.sandbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketTestClient {

    public final List<TextMessage> messages = new ArrayList<>();
    private WebSocketSession session;

    public void connect(String url, CountDownLatch countDownLatch) throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        session = client.doHandshake(new WebSocketTestHandler(countDownLatch), url).get();
    }

    private class WebSocketTestHandler extends TextWebSocketHandler {

        private final CountDownLatch countDownLatch;

        public WebSocketTestHandler(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) {
            messages.add(message);
            countDownLatch.countDown();
        }
    }

    public void closeSession() throws IOException {
        session.close();
    }
}
