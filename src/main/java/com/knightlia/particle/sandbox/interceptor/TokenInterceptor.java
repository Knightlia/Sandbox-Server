package com.knightlia.particle.sandbox.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knightlia.particle.sandbox.model.RequiresToken;
import com.knightlia.particle.sandbox.model.response.Response;
import com.knightlia.particle.sandbox.websocket.TokenHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class TokenInterceptor implements HandlerInterceptor {

    private final TokenHandler tokenHandler;
    private final ObjectMapper objectMapper;

    public TokenInterceptor(TokenHandler tokenHandler, ObjectMapper objectMapper) {
        this.tokenHandler = tokenHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod) {
            RequiresToken requiresToken = ((HandlerMethod) handler).getMethodAnnotation(RequiresToken.class);
            if (requiresToken != null) {
                final String token = request.getHeader("ST");
                if (token != null && !token.equals("")) {
                    final boolean tokenVerified = tokenHandler.verifyToken(token);

                    if (!tokenVerified) {
                        response.setContentType(APPLICATION_JSON_VALUE);
                        response.getWriter().write(objectMapper.writeValueAsString(Response.builder()
                            .status(false)
                            .message("Non-existent or invalid token specified.")
                            .build()));
                    }

                    return tokenVerified;
                }
            }
        }

        return true;
    }
}
