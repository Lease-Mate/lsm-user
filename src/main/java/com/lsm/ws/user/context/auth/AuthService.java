package com.lsm.ws.user.context.auth;

import com.lsm.ws.user.configuration.exception.InvalidCredentialsException;
import com.lsm.ws.user.configuration.exception.UserAlreadyExistsException;
import com.lsm.ws.user.context.auth.dto.AuthResponse;
import com.lsm.ws.user.context.auth.dto.LoginRequest;
import com.lsm.ws.user.context.auth.dto.RegisterRequest;
import com.lsm.ws.user.domain.cache.JwtBlacklistStore;
import com.lsm.ws.user.domain.user.User;
import com.lsm.ws.user.domain.user.UserRepository;
import com.lsm.ws.user.domain.user.UserRole;
import com.lsm.ws.user.infrastructure.jwt.JwtService;
import com.lsm.ws.user.infrastructure.rest.context.RequestContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RequestContext requestContext;
    private final JwtBlacklistStore jwtBlacklistStore;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RequestContext requestContext,
            JwtBlacklistStore jwtBlacklistStore) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.requestContext = requestContext;
        this.jwtBlacklistStore = jwtBlacklistStore;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.email().toLowerCase(Locale.getDefault()))
                      .ifPresent(UserAlreadyExistsException::new);

        var user = User.builder()
                       .withId(UUID.randomUUID().toString())
                       .withEmail(request.email())
                       .withName(request.name())
                       .withSurname(request.surname())
                       .withDateOfBirth(request.dateOfBirth())
                       .withRole(UserRole.USER)
                       .withPassword(passwordEncoder.encode(request.password()).getBytes())
                       .build();
        user = userRepository.save(user);

        var tokenPair = jwtService.generateUserWebToken(user);
        return AuthResponse.from(tokenPair);
    }

    public AuthResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.email())
                                 .orElseThrow(InvalidCredentialsException::new);

        if (!arePasswordMatching(request.password(), user.password())) {
            throw new InvalidCredentialsException();
        }

        var tokenPair = jwtService.generateUserWebToken(user);
        return AuthResponse.from(tokenPair);
    }

    private boolean arePasswordMatching(String password, byte[] encodedPassword) {
        return passwordEncoder.matches(password, new String(encodedPassword));
    }

    public AuthResponse refresh() {
        var tokenPair = jwtService.refreshToken(requestContext.originalToken());
        return AuthResponse.from(tokenPair);
    }

    public void logout() {
        jwtBlacklistStore.put(requestContext.originalToken());
    }
}
