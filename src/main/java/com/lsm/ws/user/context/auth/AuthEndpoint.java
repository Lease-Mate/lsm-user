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

@RestController
@RequestMapping("/v1/api/user/auth")
@Tag(name = "Uwierzytelnianie")
public class AuthEndpoint {

    private final AuthService authService;

    public AuthEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Rejestracja", description = "Rejestruje nowego użytkownika")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Logowanie", description = "loguje użytkownika wydając token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Odświeżenie sesji", description = "odświeża token JWT, wymaga tokenu JWT refresh")
    @JwtAccess({JwtType.REFRESH})
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh() {
        return ResponseEntity.ok(authService.refresh());
    }

    @Operation(summary = "Wylogowanie", description = "Wrzuca token JWT na czarną listę")
    @JwtAccess({JwtType.WEB})
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
