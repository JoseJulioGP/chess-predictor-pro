package com.ajedrez.app.auth.application.usecase;

import com.ajedrez.app.auth.application.dto.AuthResult;
import com.ajedrez.app.auth.application.dto.LoginUserCommand;
import com.ajedrez.app.auth.application.dto.RegisterUserCommand;
import com.ajedrez.app.auth.application.port.input.AuthenticationUseCase;
import com.ajedrez.app.auth.application.port.output.PasswordHasher;
import com.ajedrez.app.auth.application.port.output.TokenProvider;
import com.ajedrez.app.auth.domain.exception.EmailAlreadyExistsException;
import com.ajedrez.app.auth.domain.exception.InvalidCredentialsException;
import com.ajedrez.app.auth.domain.model.User;
import com.ajedrez.app.auth.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orquestador del caso de uso de autenticación.
 */
@Service
public class AuthenticationService implements AuthenticationUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordHasher passwordHasher,
                                 TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public AuthResult register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException(command.email());
        }

        String hashedPassword = passwordHasher.hash(command.rawPassword());
        User newUser = User.create(command.email(), command.username(), hashedPassword);
        User savedUser = userRepository.save(newUser);

        var token = tokenProvider.generateFor(savedUser);

        return new AuthResult(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                savedUser.getRole(),
                token.token(),
                token.expiresInMs()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResult login(LoginUserCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(command.rawPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        var token = tokenProvider.generateFor(user);

        return new AuthResult(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                token.token(),
                token.expiresInMs()
        );
    }
}
