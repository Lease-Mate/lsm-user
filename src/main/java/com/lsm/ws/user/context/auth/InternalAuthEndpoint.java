package com.lsm.ws.user.context.auth;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user/internal/auth")
public class InternalAuthEndpoint {

    @Operation(hidden = true)
    @PostMapping("/verify")
    public ResponseEntity<Void> verify() {
        return ResponseEntity.ok().build();
    }
}
