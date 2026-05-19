package com.ajedrez.app.auth.infrastructure.rest.dto;

import com.ajedrez.app.auth.domain.model.User;

import java.util.UUID;

public record UserInfoDTO(
        UUID userId,
        String email,
        String username,
        String role
) {
    public static UserInfoDTO from(User user) {
        return new UserInfoDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().name()
        );
    }
}
