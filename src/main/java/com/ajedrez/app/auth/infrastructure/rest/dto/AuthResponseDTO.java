package com.ajedrez.app.auth.infrastructure.rest.dto;

import com.ajedrez.app.auth.application.dto.AuthResult;

import java.util.UUID;

public record AuthResponseDTO(
        UUID userId,
        String email,
        String username,
        String role,
        String token,
        long expiresInMs
) {
    public static AuthResponseDTO fromResult(AuthResult result) {
        return new AuthResponseDTO(
                result.userId(),
                result.email(),
                result.username(),
                result.role().name(),
                result.token(),
                result.expiresInMs()
        );
    }
}
