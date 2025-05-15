package com.campusmov.platform.profileservice.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record VehicleIdentification(
        String vin,
        String licensePlate
) {
    public VehicleIdentification {
        if (vin == null || vin.isBlank()) {
            throw new IllegalArgumentException("VIN cannot be null or blank");
        }
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be null or blank");
        }
    }
}
