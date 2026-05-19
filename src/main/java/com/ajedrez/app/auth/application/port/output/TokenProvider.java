package com.ajedrez.app.auth.application.port.output;

import com.ajedrez.app.auth.domain.model.User;

/**
 * Puerto de salida: abstracción del proveedor de tokens.
 * Hoy es JWT; mañana podría ser PASETO, sesiones opacas o algún otro mecanismo.
 */
public interface TokenProvider {

    GeneratedToken generateFor(User user);

    record GeneratedToken(String token, long expiresInMs) {}
}
