package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.Year;

@Embeddable
public record BasicVehicleSpecs(
        String brand,
        String model,
        Integer year
) {
    public BasicVehicleSpecs {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or blank");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or blank");
        }
        if (year == null || year < 1886 || year> Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be a valid year");
        }
    }
}
