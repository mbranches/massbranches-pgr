package com.massbranches.pgr_api.entity;

import com.massbranches.pgr_api.entity.enums.RoleType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType = RoleType.USER;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessionalProfileEntity> professionalProfiles = new ArrayList<>();

    public UserEntity(String fullName, String email, String password) {
        this(fullName, email, password, RoleType.USER);
    }

    public UserEntity(String fullName, String email, String password, RoleType roleType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
    }

    public List<ProfessionalProfileEntity> getProfessionalProfiles() {
        return Collections.unmodifiableList(professionalProfiles);
    }

    public ProfessionalProfileEntity addProfessionalProfile(
            String professionalTitle,
            String councilName,
            String registrationNumber,
            String registrationState,
            String nationalRegistration
    ) {
        ProfessionalProfileEntity professionalProfile = new ProfessionalProfileEntity(
                this,
                professionalTitle,
                councilName,
                registrationNumber,
                registrationState,
                nationalRegistration
        );

        professionalProfiles.add(professionalProfile);
        return professionalProfile;
    }

    public void removeProfessionalProfile(ProfessionalProfileEntity professionalProfile) {
        professionalProfiles.remove(professionalProfile);
    }

    public void updateAccessIdentity(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeRole(RoleType roleType) {
        this.roleType = roleType;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
