package com.massbranches.pgr_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O campo 'email' é obrigatório")
        @Email(message = "O campo 'email' deve ser um endereço de email válido")
        String email,
        @NotBlank(message = "O campo 'password' é obrigatório")
        String password
) {
}
