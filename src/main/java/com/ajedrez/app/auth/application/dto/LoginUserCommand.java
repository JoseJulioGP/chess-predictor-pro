package com.ajedrez.app.auth.application.dto;

public record LoginUserCommand(
        String email,
        String rawPassword
) {}