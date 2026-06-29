package com.securetrack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                {
                    "name": "SecureTrack API",
                    "version": "1.0.0",
                    "description": "API REST para gestión de incidencias con JWT",
                    "swagger": "/swagger-ui/index.html",
                    "endpoints": {
                        "register": "POST /api/auth/register",
                        "login": "POST /api/auth/login"
                    }
                }
                """;
    }
}