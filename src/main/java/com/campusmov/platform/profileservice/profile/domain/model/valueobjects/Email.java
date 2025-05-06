package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;
import jakarta.persistence.Embeddable;

@Embeddable
public record Email(
        String emailAddress,
        boolean isVerified
) {
    public Email {
        if (emailAddress == null || emailAddress.isBlank()) {
            throw new IllegalArgumentException("Email address is required");
        }
    }
}
