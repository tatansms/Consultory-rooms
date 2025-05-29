package com.datos.umag.consultorioMedico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,

        @NotBlank(message = "Email cannot be empty")
        @Size(max = 50, message = "Email cannot exceed 50 characters")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
        String password,

        Set<String> roles // Opcional, para especificar roles al registrar
) {}