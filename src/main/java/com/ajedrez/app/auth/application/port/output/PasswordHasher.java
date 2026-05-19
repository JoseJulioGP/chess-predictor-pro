package com.ajedrez.app.auth.application.port.output;

/**
 * Puerto de salida: abstracción del mecanismo de hashing de contraseñas.
 * La capa de aplicación NO debe conocer si se usa BCrypt, Argon2 o cualquier otro.
 * Esto permite cambiar la implementación sin tocar la lógica de negocio (OCP).
 */
public interface PasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}