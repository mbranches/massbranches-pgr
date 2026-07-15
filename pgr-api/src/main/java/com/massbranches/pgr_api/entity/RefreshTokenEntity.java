package com.massbranches.pgr_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshTokenEntity extends BaseEntity {
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    @Column(nullable = false)
    private Instant expiration;

    @Builder.Default
    @Column(nullable = false)
    private Boolean revoked = false;

    private LocalDateTime revokedAt;

    public void revoke() {
        this.revoked = true;
        this.revokedAt = LocalDateTime.now();
    }
}
