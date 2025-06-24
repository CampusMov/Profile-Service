package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

import lombok.Builder;

@Builder
public record VehicleResource(
        String id, String brand, String model,
        Integer year, String status, String ownerId,
        String vin, String licensePlate
) {
}

