package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record Coordinates(
        @NotNull
        double latitude,
        @NotNull
        double longitude
) {

}

