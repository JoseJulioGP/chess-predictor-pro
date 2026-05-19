package com.ajedrez.app.auth.infrastructure.persistence;

import com.ajedrez.app.auth.domain.model.User;

/**
 * Traductor entre el modelo de dominio y la entidad JPA.
 */
final class UserMapper {

    private UserMapper() {}

    static UserJpaEntity toJpa(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    static User toDomain(UserJpaEntity entity) {
        return User.reconstitute(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
