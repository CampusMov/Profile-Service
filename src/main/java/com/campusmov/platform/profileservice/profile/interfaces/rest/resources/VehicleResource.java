package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

public record VehicleResource(
        String id, String brand, String model,
        Integer year, String status, String ownerId,
        String vin, String licensePlate
) {
}

