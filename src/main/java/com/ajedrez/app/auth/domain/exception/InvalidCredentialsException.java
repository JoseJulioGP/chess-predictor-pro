package com.ajedrez.app.auth.domain.exception;

import com.ajedrez.app.shared.domain.exception.DomainException;

public class InvalidCredentialsException extends DomainException {

    private static final String ERROR_CODE = "AUTH_INVALID_CREDENTIALS";

    public InvalidCredentialsException() {
        super("Credenciales inválidas");
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }
}