package com.massbranches.pgr_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "O campo 'refreshToken' é obrigatório") String refreshToken
) {
}
