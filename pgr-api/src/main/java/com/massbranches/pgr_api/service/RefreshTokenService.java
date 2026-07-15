package com.massbranches.pgr_api.service;

import com.massbranches.pgr_api.dto.request.RefreshTokenRequest;
import com.massbranches.pgr_api.dto.response.RefreshTokenResponse;
import com.massbranches.pgr_api.entity.RefreshTokenEntity;
import com.massbranches.pgr_api.entity.UserEntity;
import com.massbranches.pgr_api.exception.NotFoundException;
import com.massbranches.pgr_api.exception.UnauthorizedException;
import com.massbranches.pgr_api.repository.RefreshTokenRepository;
import com.massbranches.pgr_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Value("${refresh-token.expiration}")
    private Long expiration;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generate(Long userId) {
        String refreshToken = UUID.randomUUID().toString();

        Instant refreshTokenExpiration = Instant.now().plusMillis(expiration);

        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .token(refreshToken)
                .userId(userId)
                .expiration(refreshTokenExpiration)
                .build();

        refreshTokenRepository.save(entity);

        return refreshToken;
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        RefreshTokenEntity entity = refreshTokenRepository.findByTokenAndRevokedIsFalse(request.refreshToken())
                .orElseThrow(() -> new UnauthorizedException("Token de refresh inválido"));

        Long userId = entity.getUserId();

        UserEntity user = userRepository.findByIdAndActiveIsTrue(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = generate(userId);

        entity.revoke();

        refreshTokenRepository.save(entity);

        return new RefreshTokenResponse(accessToken, refreshToken);
    }
}
