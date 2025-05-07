package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(
        String countryCode,
        String number
) {
    public PhoneNumber {
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("Country code is required");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }
    }
}
