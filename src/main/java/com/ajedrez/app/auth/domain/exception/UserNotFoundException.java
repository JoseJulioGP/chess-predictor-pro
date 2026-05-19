package com.ajedrez.app.auth.domain.exception;

import com.ajedrez.app.shared.domain.exception.DomainException;

public class UserNotFoundException extends DomainException {

    private static final String ERROR_CODE = "AUTH_USER_NOT_FOUND";

    public UserNotFoundException(String identifier) {
        super("Usuario no encontrado: " + identifier);
    }

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }
}