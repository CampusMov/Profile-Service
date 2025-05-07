package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record ContactInformation(
        @Embedded
        Email email,
        @Embedded
        PhoneNumber phone

) {
    public ContactInformation {
        if (email   == null) throw new IllegalArgumentException("Email cannot be null");
        if (phone   == null) throw new IllegalArgumentException("Phone cannot be null");
    }
}
