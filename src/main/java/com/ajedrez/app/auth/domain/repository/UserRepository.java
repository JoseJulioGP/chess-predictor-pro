package com.ajedrez.app.auth.domain.repository;

import com.ajedrez.app.auth.domain.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida que define el contrato de persistencia para User.
 * La implementación concreta (adapter JPA) vive en infrastructure/.
 * Esta interfaz es propiedad del dominio: el dominio dicta lo que necesita,
 * no se acomoda a lo que JPA o cualquier ORM le ofrezca (Inversión de Dependencias).
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}