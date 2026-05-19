package com.ajedrez.app.auth.infrastructure.rest;

import com.ajedrez.app.auth.application.dto.AuthResult;
import com.ajedrez.app.auth.application.dto.LoginUserCommand;
import com.ajedrez.app.auth.application.dto.RegisterUserCommand;
import com.ajedrez.app.auth.application.port.input.AuthenticationUseCase;
import com.ajedrez.app.auth.domain.exception.UserNotFoundException;
import com.ajedrez.app.auth.domain.model.User;
import com.ajedrez.app.auth.domain.repository.UserRepository;
import com.ajedrez.app.auth.infrastructure.rest.dto.AuthResponseDTO;
import com.ajedrez.app.auth.infrastructure.rest.dto.LoginRequestDTO;
import com.ajedrez.app.auth.infrastructure.rest.dto.RegisterRequestDTO;
import com.ajedrez.app.auth.infrastructure.rest.dto.UserInfoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationUseCase authenticationUseCase;
    private final UserRepository userRepository;

    public AuthController(AuthenticationUseCase authenticationUseCase,
                          UserRepository userRepository) {
        this.authenticationUseCase = authenticationUseCase;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthResult result = authenticationUseCase.register(
                new RegisterUserCommand(request.email(), request.username(), request.password())
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AuthResponseDTO.fromResult(result));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResult result = authenticationUseCase.login(
                new LoginUserCommand(request.email(), request.password())
        );
        return ResponseEntity.ok(AuthResponseDTO.fromResult(result));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> currentUser(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        return ResponseEntity.ok(UserInfoDTO.from(user));
    }
}
