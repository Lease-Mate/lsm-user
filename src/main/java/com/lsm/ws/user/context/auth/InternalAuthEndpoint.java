package com.lsm.ws.user.context.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_SERVICES;
import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_VERIFY;
import static com.lsm.ws.user.configuration.swagger.SWC.AUTH_VERIFY_DESC;

@RestController
@RequestMapping("/v1/api/user/internal/auth")
@Tag(name = AUTH_SERVICES)
public class InternalAuthEndpoint {

    @Operation(summary = AUTH_VERIFY, description = AUTH_VERIFY_DESC)
    @PostMapping("/verify")
    public ResponseEntity<Void> verify() {
        return ResponseEntity.ok().build();
    }
}
