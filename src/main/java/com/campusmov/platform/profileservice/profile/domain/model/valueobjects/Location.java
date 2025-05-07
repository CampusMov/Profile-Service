package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record Location(
        @NotNull
        String name,

        @Embedded
        Coordinates coordinates,

        @NotNull
        String address
) {

}

