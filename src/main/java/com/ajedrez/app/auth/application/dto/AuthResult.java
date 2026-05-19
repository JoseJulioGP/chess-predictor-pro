package com.ajedrez.app.auth.application.dto;

import com.ajedrez.app.auth.domain.model.UserRole;

import java.util.UUID;

/**
 * Resultado de una operación de autenticación.
 * Contiene los datos que la capa de presentación necesita para responder al cliente.
 */
public record AuthResult(
        UUID userId,
        String email,
        String username,
        UserRole role,
        String token,
        long expiresInMs
) {}