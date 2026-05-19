package com.ajedrez.app.auth.application.port.input;

import com.ajedrez.app.auth.application.dto.AuthResult;
import com.ajedrez.app.auth.application.dto.LoginUserCommand;
import com.ajedrez.app.auth.application.dto.RegisterUserCommand;

/**
 * Puerto de entrada: define las operaciones que el mundo exterior
 * (controllers, CLI, jobs) puede invocar sobre el contexto de autenticación.
 *
 * SOLID-Interface Segregation: solo expone lo necesario, agrupado por cohesión.
 */
public interface AuthenticationUseCase {

    AuthResult register(RegisterUserCommand command);

    AuthResult login(LoginUserCommand command);
}