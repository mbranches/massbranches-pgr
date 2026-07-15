package com.massbranches.pgr_api.service;

import com.massbranches.pgr_api.dto.request.LoginRequest;
import com.massbranches.pgr_api.dto.response.LoginResponse;
import com.massbranches.pgr_api.entity.UserEntity;
import com.massbranches.pgr_api.config.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse execute(LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserEntity user = userDetails.getUser();
        Long userId = user.getId();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.generate(userId);

        return new LoginResponse(accessToken, refreshToken);
    }
}
