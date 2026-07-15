package com.massbranches.pgr_api.dto.response;

public record LoginResponse(String accessToken,
                            String refreshToken) {
}
