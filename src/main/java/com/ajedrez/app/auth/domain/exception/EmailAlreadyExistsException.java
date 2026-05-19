package com.ajedrez.app.auth.domain.exception;

import com.ajedrez.app.shared.domain.exception.DomainException;

public class EmailAlreadyExistsException extends DomainException {

    private static final String ERROR_CODE = "AUTH_EMAIL_EXISTS";

    public EmailAlreadyExistsException(String email) {
        super("El email '" + email + "' ya está registrado");
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }
}