package com.ajedrez.app.shared.domain.exception;

/**
 * Excepción base para errores de dominio.
 * Todas las excepciones de reglas de negocio extienden de esta clase
 * para permitir un manejo uniforme en el GlobalExceptionHandler.
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getErrorCode();
}