package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.*;

@Embeddable
public record UserId(
        Long id
) {
    public UserId {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("userId cannot be null or less than or equal to 0");
        }
    }
}
