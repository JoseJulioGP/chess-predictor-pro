package com.ajedrez.app.shared.infrastructure.rest;

import java.time.Instant;
import java.util.List;

/**
 * Estructura uniforme de errores para toda la API REST.
 */
public record ErrorResponse(
        Instant timestamp,
        int status,
        String errorCode,
        String message,
        List<String> details
) {
    public static ErrorResponse of(int status, String errorCode, String message) {
        return new ErrorResponse(Instant.now(), status, errorCode, message, List.of());
    }

    public static ErrorResponse of(int status, String errorCode, String message, List<String> details) {
        return new ErrorResponse(Instant.now(), status, errorCode, message, details);
    }
}