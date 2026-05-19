package com.ajedrez.app.auth.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Entidad de Dominio User.
 *
 * Responsable de mantener sus propias invariantes (GRASP: Information Expert).
 * No tiene dependencias de Spring, JPA ni ninguna infraestructura externa.
 * Encapsulamiento riguroso: sin setters publicos; los cambios ocurren mediante
 * metodos de negocio con nombres expresivos (changeUsername, promoteTo).
 */
public class User {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 30;

    private final UUID id;
    private final String email;
    private String username;
    private String passwordHash;
    private UserRole role;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(UUID id, String email, String username, String passwordHash,
                 UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method para crear un nuevo usuario (GRASP: Creator).
     * Valida todas las invariantes antes de construir la instancia.
     */
    public static User create(String email, String username, String passwordHash) {
        validateEmail(email);
        validateUsername(username);
        validatePasswordHash(passwordHash);

        LocalDateTime now = LocalDateTime.now();
        return new User(
                UUID.randomUUID(),
                email.toLowerCase().trim(),
                username.trim(),
                passwordHash,
                UserRole.PLAYER,
                now,
                now
        );
    }

    /**
     * Reconstituye un User desde persistencia.
     * Unicamente debe ser invocado por adapters de infraestructura.
     */
    public static User reconstitute(UUID id, String email, String username,
                                    String passwordHash, UserRole role,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        Objects.requireNonNull(id, "id no puede ser nulo");
        return new User(id, email, username, passwordHash, role, createdAt, updatedAt);
    }

    public void changeUsername(String newUsername) {
        validateUsername(newUsername);
        this.username = newUsername.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void promoteTo(UserRole newRole) {
        Objects.requireNonNull(newRole, "El rol no puede ser nulo");
        this.role = newRole;
        this.updatedAt = LocalDateTime.now();
    }

    // ============ VALIDACIONES INTERNAS ============

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de email invalido: " + email);
        }
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        String trimmed = username.trim();
        if (trimmed.length() < MIN_USERNAME_LENGTH || trimmed.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException(
                    "El username debe tener entre " + MIN_USERNAME_LENGTH +
                    " y " + MAX_USERNAME_LENGTH + " caracteres"
            );
        }
    }

    private static void validatePasswordHash(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("El hash de la contrasena es obligatorio");
        }
    }

    // ============ GETTERS (sin setters por diseno) ============

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}