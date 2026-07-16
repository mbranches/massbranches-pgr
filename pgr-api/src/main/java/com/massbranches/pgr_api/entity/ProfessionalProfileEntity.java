package com.massbranches.pgr_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uk_professional_profile_registration",
                columnNames = {
                        "user_id",
                        "professional_title",
                        "council_name",
                        "registration_number",
                        "registration_state"
                }
        )
)
public class ProfessionalProfileEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "professional_title", nullable = false)
    private String professionalTitle;

    @Column(name = "council_name", nullable = false)
    private String councilName;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "registration_state", length = 2)
    private String registrationState;

    @Column(name = "national_registration")
    private String nationalRegistration;

    ProfessionalProfileEntity(
            UserEntity user,
            String professionalTitle,
            String councilName,
            String registrationNumber,
            String registrationState,
            String nationalRegistration
    ) {
        this.user = user;
        this.professionalTitle = professionalTitle;
        this.councilName = councilName;
        this.registrationNumber = registrationNumber;
        this.registrationState = registrationState;
        this.nationalRegistration = nationalRegistration;
    }

    public void updateRegistration(
            String professionalTitle,
            String councilName,
            String registrationNumber,
            String registrationState,
            String nationalRegistration
    ) {
        this.professionalTitle = professionalTitle;
        this.councilName = councilName;
        this.registrationNumber = registrationNumber;
        this.registrationState = registrationState;
        this.nationalRegistration = nationalRegistration;
    }
}
