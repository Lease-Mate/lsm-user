package com.lsm.ws.user.context.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public record LoginRequest(@Email
                           @NotEmpty
                           String email,
                           @NotEmpty
                           String password) {
}
