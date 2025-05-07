package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(
        String institutionalEmailAddress,
        String personalEmailAddress
) {
    public Email {
        if (institutionalEmailAddress == null || institutionalEmailAddress.isBlank()) {
            throw new IllegalArgumentException("Institutional email address cannot be null or blank");
        }
        if (personalEmailAddress == null || personalEmailAddress.isBlank()) {
            throw new IllegalArgumentException("Personal email address cannot be null or blank");
        }
    }
}
