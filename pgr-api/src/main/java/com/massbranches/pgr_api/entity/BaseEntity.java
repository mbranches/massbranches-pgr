package com.massbranches.pgr_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    
    /**
     * Internal primary key - never exposed to clients.
     * Used for database joins and internal operations.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Public identifier for API exposure.
     * Generated automatically on entity creation.
     * Used in all API responses and requests.
     */
    @Column(name = "public_id", unique = true, nullable = false, updatable = false, length = 36)
    private String publicId;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Generates a public ID before persisting the entity.
     * Called automatically by JPA lifecycle callbacks.
     */
    @PrePersist
    protected void generatePublicId() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID().toString();
        }
    }
}
