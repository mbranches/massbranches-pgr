package com.massbranches.pgr_api.dto.response;

public record RefreshTokenResponse(String accessToken,
                                   String refreshToken) {
}
