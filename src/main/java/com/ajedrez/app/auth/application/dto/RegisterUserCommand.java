package com.ajedrez.app.auth.application.dto;

/**
 * Command inmutable para registrar un nuevo usuario.
 * Es un DTO de la capa de aplicación: representa la INTENCIÓN del usuario,
 * desacoplada de cualquier formato de entrada (REST, GraphQL, CLI, etc).
 */
public record RegisterUserCommand(
        String email,
        String username,
        String rawPassword
) {}