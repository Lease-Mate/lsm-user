package com.lsm.ws.user.context.auth;

import com.lsm.ws.user.context.auth.dto.AuthResponse;
import com.lsm.ws.user.context.auth.dto.LoginRequest;
import com.lsm.ws.user.context.auth.dto.RegisterRequest;
import com.lsm.ws.user.infrastructure.jwt.JwtAccess;
import com.lsm.ws.user.infrastructure.jwt.JwtType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_LOGIN;
import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_LOGIN_DESC;
import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_REGISTER;
import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_REGISTER_DESC;
import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_SERVICES;

@RestController
@RequestMapping("/v1/api/user/auth")
@Tag(name = AUTH_SERVICES)
public class AuthEndpoint {

    private final AuthService authService;

    public AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = AUTH_REGISTER, description = AUTH_REGISTER_DESC)
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = AUTH_LOGIN, description = AUTH_LOGIN_DESC)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @JwtAccess({JwtType.REFRESH})
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh() {
        return ResponseEntity.ok(authService.refresh());
    }

    @JwtAccess({JwtType.WEB})
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
