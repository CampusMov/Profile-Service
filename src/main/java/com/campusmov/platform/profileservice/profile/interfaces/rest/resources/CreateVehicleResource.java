package com.campusmov.platform.profileservice.profile.interfaces.rest.resources;

public record CreateVehicleResource(
        String brand, String model, Integer year,
        String status, String vin, String licensePlate,
        String ownerId
) {
}
