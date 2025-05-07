package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.*;

@Embeddable
public record UserId(
        String id
) {
    public UserId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
    }
}
