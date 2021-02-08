package com.knightlia.particle.sandbox.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @Value("${sandbox.version}")
    private String version;

    @GetMapping
    public String version() {
        return "Sandbox Server - " + version;
    }
}
