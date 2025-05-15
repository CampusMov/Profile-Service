package com.campusmov.platform.profileservice.profile.domain.model.commands;

import java.sql.Date;

public record CreateVehicleCommand(
        String brand, String model, Integer year,
        String status, String vin, String licensePlate,
        String ownerId
) {
    public CreateVehicleCommand {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (year == null || year < 1886 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year must be a valid year");
        }
        if (status == null || status.isBlank() || !status.matches("ACTIVE|INACTIVE|RETIRED")) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        if (vin == null || vin.isBlank() || vin.length() != 17) {
            throw new IllegalArgumentException("VIN cannot be null or empty");
        }
        if (licensePlate == null || licensePlate.isBlank() || licensePlate.length() > 10) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (ownerId == null || ownerId.isBlank()) {
            throw new IllegalArgumentException("Owner ID cannot be null or empty");
        }
    }
}
