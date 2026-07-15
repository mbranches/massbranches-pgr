package com.massbranches.pgr_api.service;

import com.massbranches.pgr_api.entity.UserEntity;
import com.massbranches.pgr_api.exception.NotFoundException;
import com.massbranches.pgr_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUserByPublicIdService {
    private final UserRepository userRepository;

    public UserEntity execute(String publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("User não encontrado com o publicId: " + publicId));
    }

}
