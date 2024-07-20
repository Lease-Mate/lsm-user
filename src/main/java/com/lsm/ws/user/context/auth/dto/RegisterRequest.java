package com.lsm.ws.user.context.auth.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Validated
public record RegisterRequest(@Email
                              String email,

                              @NotEmpty
                              String password,

                              @NotEmpty
                              String name,

                              @NotEmpty
                              String surname,

                              @NotNull
                              Date dateOfBirth) {
}
